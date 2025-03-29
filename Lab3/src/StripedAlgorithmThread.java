public class StripedAlgorithmThread extends Thread {
    private final int rowIndex;
    private final double[] row;
    private final double[] result;
    private final Matrix B;

    public StripedAlgorithmThread(double[] row, int rowIndex, Matrix B) {
        this.row = row;
        this.rowIndex = rowIndex;
        this.result = new double[B.getSizeAxis1()];
        this.B = B;
    }

    @Override
    public void run() {
        for (int j = 0; j < B.getSizeAxis1(); j++) {
            for (int i = 0; i < row.length; i++) {
                result[j] += row[i] * B.matrix[i][j];
            }
        }
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public double[] getResult() {
        return this.result;
    }
}
