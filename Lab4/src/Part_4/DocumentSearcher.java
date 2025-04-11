package Part_4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DocumentSearcher {
    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    //запуск
    public static Map<String, Map<String, Integer>> searchKeywordsInDirectory(File directory, List<String> keywords) {
        return FORK_JOIN_POOL.invoke(new KeywordSearchTask(directory, keywords));
    }


    private static class KeywordSearchTask extends RecursiveTask<Map<String, Map<String, Integer>>> {
        private final File directory;
        private final List<String> keywords;

        public KeywordSearchTask(File directory, List<String> keywords) {
            this.directory = directory;
            this.keywords = keywords;
        }

        @Override
        protected Map<String, Map<String, Integer>> compute() {
            // (слово, (шлях, кількість))
            Map<String, Map<String, Integer>> result = new HashMap<>();

            for (String keyword : keywords) {
                result.put(keyword, new HashMap<>());
            }

            List<KeywordSearchTask> subTasks = new ArrayList<>();
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        KeywordSearchTask subTask = new KeywordSearchTask(file, keywords);
                        subTask.fork();
                        subTasks.add(subTask);
                    } else if (isTextDocument(file)) {
                        processFile(file, result);
                    }
                }
            }

            for (KeywordSearchTask subTask : subTasks) {
                Map<String, Map<String, Integer>> subResult = subTask.join();
                mergeResults(result, subResult);
            }

            return result;
        }

        private boolean isTextDocument(File file) {
            String name = file.getName().toLowerCase();
            return name.endsWith(".txt") || name.endsWith(".docx");
        }


        private void processFile(File file, Map<String, Map<String, Integer>> result) {
            try {
                String content = DocumentReader.readContent(file);
                String filePath = file.getAbsolutePath();

                for (String keyword : keywords) {
                    int count = countOccurrences(content.toLowerCase(), keyword.toLowerCase());

                    if (count > 0) {
                        result.get(keyword).put(filePath, count);
                    }
                }
            } catch (Exception e) {
                System.err.println("Помилка при читанні файлу: " + file.getPath() + " - " + e.getMessage());
            }
        }

        private int countOccurrences(String text, String keyword) {
            int count = 0;
            int index = 0;
            while ((index = text.indexOf(keyword, index)) != -1) {
                count++;
                index += keyword.length();
            }
            return count;
        }


        private void mergeResults(Map<String, Map<String, Integer>> target, Map<String, Map<String, Integer>> source) {
            for (String keyword : source.keySet()) {
                Map<String, Integer> sourceFileCounts = source.get(keyword);
                Map<String, Integer> targetFileCounts = target.get(keyword);

                for (Map.Entry<String, Integer> entry : sourceFileCounts.entrySet()) {
                    targetFileCounts.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }
}