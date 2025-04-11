package Part_2;

public class Main {
    public static void main(String[] args) {
        int matrixSize = 10;

        int[] threadCounts = {2, 4, 8};

        PerformanceBenchmark benchmark = new PerformanceBenchmark(matrixSize);
        benchmark.runFullBenchmark(threadCounts);
    }
}
