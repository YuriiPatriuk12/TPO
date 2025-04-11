package Part_1;

import java.util.HashMap;
import java.util.Map;

public class WordLengthStatistics {
    private long totalWords = 0;
    private double sumLength = 0;
    private double sumSquaredLength = 0;
    private Map<Integer, Integer> histogram = new HashMap<>();

    public void addWord(int length) {
        totalWords++;
        sumLength += length;
        sumSquaredLength += length * length;

        histogram.put(length, histogram.getOrDefault(length, 0) + 1);
    }

    public void merge(WordLengthStatistics other) {
        if (other == null) return;

        totalWords += other.totalWords;
        sumLength += other.sumLength;
        sumSquaredLength += other.sumSquaredLength;

        other.histogram.forEach((length, count) -> {
            histogram.put(length, histogram.getOrDefault(length, 0) + count);
        });
    }

    public double getMean() {
        return totalWords > 0 ? sumLength / totalWords : 0;
    }

    public double getStdDev() {
        if (totalWords <= 1) return 0;

        double mean = getMean();
        double variance = (sumSquaredLength / totalWords) - (mean * mean);
        return Math.sqrt(variance);
    }

    public long getTotalWords() {
        return totalWords;
    }

    public Map<Integer, Integer> getHistogram() {
        return histogram;
    }
}