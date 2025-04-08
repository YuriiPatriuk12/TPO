package Part_3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DocumentProcessTask extends RecursiveTask<List<Set<String>>> {
    private static final Pattern WORD_PATTERN = Pattern.compile("^(?:a|[а-яА-Яa-zA-Z]{2,})$");

    private final List<String> filePaths;

    public DocumentProcessTask(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    @Override
    protected List<Set<String>> compute() {
        if (filePaths.size() <= 1) {
            List<Set<String>> result = new ArrayList<>();
            if (filePaths.size() == 1) {
                try {
                    result.add(processFile(filePaths.get(0)));
                } catch (IOException e) {
                    System.err.println("Помилка при обробці файлу " + filePaths.get(0) + ": " + e.getMessage());
                }
            }
            return result;
        }

        int mid = filePaths.size() / 2;
        DocumentProcessTask leftTask = new DocumentProcessTask(filePaths.subList(0, mid));
        DocumentProcessTask rightTask = new DocumentProcessTask(filePaths.subList(mid, filePaths.size()));

        leftTask.fork();

        List<Set<String>> rightResult = rightTask.compute();
        List<Set<String>> leftResult = leftTask.join();

        leftResult.addAll(rightResult);
        return leftResult;
    }

    private Set<String> processFile(String filePath) throws IOException {
        String content = CommonWordsSearcher.readFileContent(filePath);

        return Arrays.stream(content.toLowerCase().split("\\W+"))
                .filter(word -> !word.isEmpty())
                .filter(this::isWord)
                .collect(Collectors.toSet());
    }

    private boolean isWord(String str) {
        return WORD_PATTERN.matcher(str).matches();
    }
}