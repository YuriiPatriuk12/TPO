package Part_1;

import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TextAnalysisTask extends RecursiveTask<WordLengthStatistics> {
    private static final int THRESHOLD = 10000;
    private static final Pattern WORD_PATTERN = Pattern.compile("\\p{L}+");
    private final String text;
    private final int start;
    private final int end;

    public TextAnalysisTask(String text, int start, int end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    @Override
    protected WordLengthStatistics compute() {
        if (end - start <= THRESHOLD) {
            return computeDirectly();
        }

        int middle = start + (end - start) / 2;

        while (middle < end && Character.isLetterOrDigit(text.charAt(middle))) {
            middle++;
        }

        TextAnalysisTask leftTask = new TextAnalysisTask(text, start, middle);
        TextAnalysisTask rightTask = new TextAnalysisTask(text, middle, end);

        // Паралелізація
        leftTask.fork();
        WordLengthStatistics rightResult = rightTask.compute();
        WordLengthStatistics leftResult = leftTask.join();

        // Об'єднання
        rightResult.merge(leftResult);
        return rightResult;
    }

    private WordLengthStatistics computeDirectly() {
        WordLengthStatistics stats = new WordLengthStatistics();
        Matcher matcher = WORD_PATTERN.matcher(text.substring(start, end));

        while (matcher.find()) {
            String word = matcher.group();
            stats.addWord(word.length());
        }

        return stats;
    }
}