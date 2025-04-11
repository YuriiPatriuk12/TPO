package Part_2;

public class MatrixValidator {

    private static final double EPSILON = 1e-7;

    public static boolean areMatricesEqual(Matrix expected, Matrix actual) {
        if (expected.getRows() != actual.getRows() ||
                expected.getColumns() != actual.getColumns()) {
            return false;
        }

        for (int i = 0; i < expected.getRows(); i++) {
            for (int j = 0; j < expected.getColumns(); j++) {
                if (Math.abs(expected.get(i, j) - actual.get(i, j)) > EPSILON) {
                    return false;
                }
            }
        }

        return true;
    }
}