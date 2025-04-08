package Part_2;

public class PerformanceBenchmark {
    private final Matrix a;
    private final Matrix b;
    private final int matrixSize;

    public PerformanceBenchmark(int matrixSize) {
        this.matrixSize = matrixSize;
        this.a = MatrixGenerator.generateRandomMatrix(matrixSize, matrixSize, 0, 10);
        this.b = MatrixGenerator.generateRandomMatrix(matrixSize, matrixSize, 0, 10);
    }

    public long runOriginalStripedMultiplication(int numThreads) {
        StripedMatrixMultiplication multiplier = new StripedMatrixMultiplication(
                a.getData(), b.getData(), numThreads
        );

        long startTime = System.nanoTime();
        multiplier.multiply();
        long endTime = System.nanoTime();

        return endTime - startTime;
    }

    public long runForkJoinStripedMultiplication(int numThreads) {
        StripedForkJoinMultiplication multiplier = new StripedForkJoinMultiplication(a, b, numThreads);

        long startTime = System.nanoTime();
        multiplier.multiply();
        long endTime = System.nanoTime();

        multiplier.shutdown();
        return endTime - startTime;
    }

    public boolean verifyResults(int numThreads) {
        StripedMatrixMultiplication originalMultiplier = new StripedMatrixMultiplication(
                a.getData(), b.getData(), numThreads
        );
        StripedForkJoinMultiplication forkJoinMultiplier = new StripedForkJoinMultiplication(a, b, numThreads);

        originalMultiplier.multiply();
        Matrix originalResult = originalMultiplier.getResultMatrix();
        Matrix forkJoinResult = forkJoinMultiplier.multiply();

        forkJoinMultiplier.shutdown();
        return MatrixValidator.areMatricesEqual(originalResult, forkJoinResult);
    }

    public void runFullBenchmark(int[] threadCounts) {
        System.out.println("Розмір матриці: " + matrixSize + "x" + matrixSize);

        for (int numThreads : threadCounts) {
            System.out.println("\nТестування з " + numThreads + " потоками:");

            boolean correctResults = verifyResults(numThreads);
            System.out.println("Результати збігаються: " + correctResults);

            long originalTime = runOriginalStripedMultiplication(numThreads);
            System.out.println("Оригінальний стрічковий алгоритм: " + originalTime + " нс");

            long forkJoinTime = runForkJoinStripedMultiplication(numThreads);
            System.out.println("ForkJoin стрічковий алгоритм: " + forkJoinTime + " нс");

            double speedup = (double) originalTime / forkJoinTime;
            System.out.println("Прискорення ForkJoin: " + String.format("%.2f", speedup) + "x");
        }
    }
}