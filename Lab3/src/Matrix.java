import java.util.Arrays;

public class Matrix {
    public double[][] matrix;
    private final int sizeAxis0;
    private final int sizeAxis1;

    public Matrix(int sizeAxis0, int sizeAxis1) {
        this.matrix = new double[sizeAxis0][sizeAxis1];
        this.sizeAxis0 = sizeAxis0;
        this.sizeAxis1 = sizeAxis1;
    }

    public double[] getRow(int index) {
        return this.matrix[index];
    }

    public double[] getColumn(int index) {
        return Arrays.stream(matrix).mapToDouble(doubles -> doubles[index]).toArray();
    }

    public int getSizeAxis0() {
        return this.sizeAxis0;
    }

    public int getSizeAxis1() {
        return this.sizeAxis1;
    }

    public void generateRandomMatrix() {
        for (int i = 0; i < this.sizeAxis0; i++) {
            for (int j = 0; j < this.sizeAxis1; j++) {
                this.matrix[i][j] = Math.random();
            }
        }
    }
}
