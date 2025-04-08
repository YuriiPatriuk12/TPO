package Part_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;

public class PerformanceExperimentRunner {
    public static void main(String[] args) {
        String text = "";

        try {
            String filePath = "src/texts/input1.txt";
            text = new String(Files.readAllBytes(Paths.get(filePath)));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        runExperiment(text);
    }

    public static void runExperiment(String text) {
        int[] threadCounts = {2, 4, 8, 16};
        int iterations = 5;

        System.out.println("Розмір тексту: " + text.length() + " символів");
        System.out.println("Кількість повторень: " + iterations);

        // Базовий алгоритм
        double[] seqTimes = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            long startSeq = System.nanoTime();
            TextAnalyzer.analyzeSequentially(text);
            long endSeq = System.nanoTime();
            seqTimes[i] = (endSeq - startSeq) / 1_000_000_000.0;
        }

        double avgSeqTime = 0;
        for (double time : seqTimes) {
            avgSeqTime += time;
        }
        avgSeqTime /= iterations;

        System.out.printf("Середній послідовний час: %.5f секунд\n", avgSeqTime);
        System.out.println("\nКількість потоків | Середній час (сек) | Прискорення | Ефективність");
        System.out.println("-------------------------------------------------------------------");

        // Паралельний алгоритм
        for (int threadCount : threadCounts) {
            double[] times = new double[iterations];

            for (int i = 0; i < iterations; i++) {
                ForkJoinPool pool = new ForkJoinPool(threadCount);

                long start = System.nanoTime();
                pool.invoke(new TextAnalysisTask(text, 0, text.length()));
                long end = System.nanoTime();

                times[i] = (end - start) / 1_000_000_000.0;
            }

            double avgTime = 0;
            for (double time : times) {
                avgTime += time;
            }
            avgTime /= iterations;

            double speedup = avgSeqTime / avgTime;
            double efficiency = speedup / threadCount;

            System.out.printf("%-16d | %-18.5f | %-11.3f | %-11.3f\n",
                    threadCount, avgTime, speedup, efficiency);
        }

        System.out.println("\nТеоретична ефективність:");
        for (int threadCount : threadCounts) {
            double sequential = 0.05;
            double parallel = 1 - sequential;

            double theoreticalSpeedup = 1 / (sequential + (parallel / threadCount));
            double theoreticalEfficiency = theoreticalSpeedup / threadCount;

            System.out.printf("%d потоків - прискорення: %.3f, Ефективність: %.3f\n",
                    threadCount, theoreticalSpeedup, theoreticalEfficiency);
        }
    }
}