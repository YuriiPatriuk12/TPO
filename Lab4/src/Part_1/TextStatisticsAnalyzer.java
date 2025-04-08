package Part_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class TextStatisticsAnalyzer {
        public static void main(String[] args) {
        String text = "";

        try {
            String filePath = "src/texts/input2.txt";
            text = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // Час послідовний
        long startSeq = System.currentTimeMillis();
        TextAnalyzer.analyzeSequentially(text);
        long endSeq = System.currentTimeMillis();

        // Час паралельний
        ForkJoinPool pool = new ForkJoinPool();
        long startPar = System.currentTimeMillis();
        WordLengthStatistics parStats = pool.invoke(new TextAnalysisTask(text, 0, text.length()));
        long endPar = System.currentTimeMillis();

        double seqTime = (endSeq - startSeq) / 1000.0;
        double parTime = (endPar - startPar) / 1000.0;
        double speedup = seqTime / parTime;
        double efficiency = speedup / Runtime.getRuntime().availableProcessors();

        System.out.println("Кількість слів: " + parStats.getTotalWords());
        System.out.println("Середня довжина слова: " + parStats.getMean());
        System.out.println("Середньоквадратичне відхилення: " + parStats.getStdDev());

        Map<Integer, Integer> histogram = parStats.getHistogram();
        histogram.keySet().stream().sorted().forEach(length -> {
            int count = histogram.get(length);
            System.out.printf("%d символів: %d слів\n", length, count);
        });

        System.out.printf("Послідовний аналіз: %.3f секунд\n", seqTime);
        System.out.printf("Паралельний аналіз: %.3f секунд\n", parTime);
        System.out.printf("Прискорення: %.3f\n", speedup);
        System.out.printf("Ефективність: %.3f\n", efficiency);
    }
}