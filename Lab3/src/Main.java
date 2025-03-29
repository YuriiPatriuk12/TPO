public class Main {
    public static void main(String[] args) {
        runExperimentsFox();
        System.out.println();
        runExperimentsStriped();
    }

    public static void runExperimentsFox() {
        int[] sizesArray = new int[]{500, 1000, 1500, 2000, 2500};
        int[] threadsN = new int[]{4, 9, 25};
        int nExperiments = 1;

        System.out.printf("%10s %10s %10s %10s %10s %10s %10s %10s\n", "Matrix Size", "Serial Time", "4T Time", "4T Speedup", "9T Time", "9T Speedup", "25T Time", "25T Speedup");

        for (int size : sizesArray) {
            Matrix A = new Matrix(size, size);
            Matrix B = new Matrix(size, size);
            A.generateRandomMatrix();
            B.generateRandomMatrix();

            Algorithm ba = new BasicAlgorithm(A, B);
            long serialTime = measureExecutionTime(ba, nExperiments);

            long[] foxTimes = new long[threadsN.length];
            double[] speedups = new double[threadsN.length];

            for (int i = 0; i < threadsN.length; i++) {
                Algorithm fa = new FoxAlgorithm(A, B, threadsN[i]);
                foxTimes[i] = measureExecutionTime(fa, nExperiments);
                speedups[i] = (double) serialTime / foxTimes[i];
            }

            System.out.printf("%10d %10d", size, serialTime);
            for (int i = 0; i < threadsN.length; i++) {
                System.out.printf(" %10d %10.2f", foxTimes[i], speedups[i]);
            }
            System.out.println();
        }
    }

    public static void runExperimentsStriped() {
        int[] sizesArray = new int[]{500, 1000, 1500, 2000, 2500};
        int[] threadsN = new int[]{4, 9, 25};
        int nExperiments = 10;

        System.out.printf("%10s %10s %10s %10s %10s %10s %10s %10s\n", "Matrix Size", "Serial Time", "4T Time", "4T Speedup", "9T Time", "9T Speedup", "25T Time", "25T Speedup");

        for (int size : sizesArray) {
            Matrix A = new Matrix(size, size);
            Matrix B = new Matrix(size, size);
            A.generateRandomMatrix();
            B.generateRandomMatrix();

            Algorithm ba = new BasicAlgorithm(A, B);
            long serialTime = measureExecutionTime(ba, nExperiments);

            long[] foxTimes = new long[threadsN.length];
            double[] speedups = new double[threadsN.length];

            for (int i = 0; i < threadsN.length; i++) {
                Algorithm fa = new StripedAlgorithm(A, B, threadsN[i]);
                foxTimes[i] = measureExecutionTime(fa, nExperiments);
                speedups[i] = (double) serialTime / foxTimes[i];
            }

            System.out.printf("%10d %10d", size, serialTime);
            for (int i = 0; i < threadsN.length; i++) {
                System.out.printf(" %10d %10.2f", foxTimes[i], speedups[i]);
            }
            System.out.println();
        }
    }

    private static long measureExecutionTime(Algorithm algorithm, int nExperiments) {
        long acc = 0;
        for (int i = 0; i < nExperiments; i++) {
            long startTime = System.nanoTime();
            algorithm.multiply();
            acc += System.nanoTime() - startTime;
        }
        return acc / (nExperiments * 1_000_000); // Convert to milliseconds
    }

}
