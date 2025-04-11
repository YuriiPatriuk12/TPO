package Part_2;

public class StripedMatrixMultiplication {
    private final double[][] a;
    private final double[][] b;
    private final double[][] c;
    private final int size;
    private final int threadsCount;
    private final Thread[] threads;

    public StripedMatrixMultiplication(double[][] a, double[][] b, int threadsCount) {
        this.a = a;
        this.b = b;
        this.size = a.length;
        this.c = new double[size][size];
        this.threadsCount = threadsCount;
        this.threads = new Thread[threadsCount];
    }

    public double[][] multiply() {
        for (int i = 0; i < threadsCount; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> multiplyStrip(threadIndex));
            threads[i].start();
        }

        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return c;
    }

    private void multiplyStrip(int stripIndex) {
        for (int i = stripIndex; i < size; i += threadsCount) {
            for (int j = 0; j < size; j++) {
                double sum = 0;
                for (int k = 0; k < size; k++) {
                    sum += a[i][k] * b[k][j];
                }
                c[i][j] = sum;
            }
        }
    }

    public Matrix getResultMatrix() {
        return new Matrix(c);
    }
}