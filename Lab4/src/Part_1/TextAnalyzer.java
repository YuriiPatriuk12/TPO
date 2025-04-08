package Part_1;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TextAnalyzer {
    private static final Pattern WORD_PATTERN = Pattern.compile("\\p{L}+");

    public static WordLengthStatistics analyzeSequentially(String text) {
        WordLengthStatistics stats = new WordLengthStatistics();
        Matcher matcher = WORD_PATTERN.matcher(text);

        while (matcher.find()) {
            String word = matcher.group();
            stats.addWord(word.length());
        }

        return stats;
    }
}