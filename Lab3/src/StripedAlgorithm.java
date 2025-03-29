import java.util.*;

public class StripedAlgorithm implements Algorithm {
    Matrix A;
    Matrix B;
    private final int nThread;

    public StripedAlgorithm(Matrix A, Matrix B, int nThread) {
        this.A = A;
        this.B = B;
        this.nThread = nThread;
    }

    @Override
    public Matrix multiply() {
        Matrix C = new Matrix(A.getSizeAxis0(), B.getSizeAxis1());

        C = parallelize(C);

        return C;
    }

   public Matrix parallelize(Matrix C) {
        ArrayList<StripedAlgorithmThread> threads = new ArrayList<>();

        for (int i = 0; i < A.getSizeAxis0(); i++) {
            StripedAlgorithmThread t = new StripedAlgorithmThread(A.getRow(i), i, B);
            threads.add(t);
            threads.get(i).start();
        }

        for (StripedAlgorithmThread t : threads) {
            try {
                t.join();
                C.matrix[t.getRowIndex()] = t.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return C;
    }
}
