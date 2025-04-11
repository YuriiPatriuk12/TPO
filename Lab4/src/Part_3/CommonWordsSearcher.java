package Part_3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class CommonWordsSearcher {

    public static void main(String[] args) {
        List<String> filePaths = new ArrayList<>();

        filePaths.add("src/texts/input1.txt");
        filePaths.add("src/texts/input2.txt");
        filePaths.add("src/texts/input3.txt");

        for (String path : filePaths) {
            Path filePath = Paths.get(path);
            if (!Files.exists(filePath)) {
                System.err.println("Файл не знайдено: " + path);
                try {
                    Files.createDirectories(filePath.getParent());
                    Files.createFile(filePath);
                } catch (IOException e) {
                    System.err.println("Не вдалося створити тестовий файл: " + e.getMessage());
                    return;
                }
            }
        }

        try {
            CommonWordsSearcher searcher = new CommonWordsSearcher();
            Set<String> commonWords = searcher.findCommonWords(filePaths);

            if (commonWords.isEmpty()) {
                System.out.println("Не знайдено спільних слів у всіх файлах.");
            } else {
                System.out.println("Знайдено " + commonWords.size() + " спільних слів у всіх файлах:");
                commonWords.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.err.println("Виникла помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Set<String> findCommonWords(List<String> filePaths) {
        if (filePaths == null || filePaths.isEmpty()) {
            return new HashSet<>();
        }
        // створення і запуск
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Set<String>> allWordSets = pool.invoke(new DocumentProcessTask(filePaths));

        if (allWordSets.isEmpty()) {
            return new HashSet<>();
        }

        // перший набір слів
        Set<String> commonWords = new HashSet<>(allWordSets.get(0));

        for (int i = 1; i < allWordSets.size(); i++) {
            commonWords.retainAll(allWordSets.get(i));
        }

        return commonWords;
    }

    public static String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Path.of(filePath)));
    }
}