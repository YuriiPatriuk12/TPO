package Part_2;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixGenerator {
    public static Matrix generateRandomMatrix(int rows, int cols, double min, double max) {
        Matrix matrix = new Matrix(rows, cols);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.set(i, j, random.nextDouble(min, max));
            }
        }

        return matrix;
    }
}