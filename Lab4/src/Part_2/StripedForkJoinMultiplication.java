package Part_2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class StripedForkJoinMultiplication {
    private final Matrix a;
    private final Matrix b;
    private final Matrix result;
    private final int size;
    private final ForkJoinPool pool;

    public StripedForkJoinMultiplication(Matrix a, Matrix b, int numThreads) {
        this.a = a;
        this.b = b;
        this.size = a.getRows();
        this.result = new Matrix(size, size);
        this.pool = new ForkJoinPool(numThreads);
    }

    // Запуск
    public Matrix multiply() {
        pool.invoke(new StripedMultiplicationTask(0, size));
        return result;
    }

    public void shutdown() {
        pool.shutdown();
    }

    private class StripedMultiplicationTask extends RecursiveAction {
        private static final int THRESHOLD = 32;
        private final int startRow;
        private final int endRow;

        public StripedMultiplicationTask(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        protected void compute() {
            if (endRow - startRow <= THRESHOLD) {
                computeDirectly();
            } else {
                // розділення
                int middle = startRow + (endRow - startRow) / 2;
                invokeAll(
                        new StripedMultiplicationTask(startRow, middle),
                        new StripedMultiplicationTask(middle, endRow)
                );
            }
        }

        private void computeDirectly() {
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < size; j++) {
                    double sum = 0;
                    for (int k = 0; k < size; k++) {
                        sum += a.get(i, k) * b.get(k, j);
                    }
                    result.set(i, j, sum);
                }
            }
        }
    }
}