package Part_4;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String directoryPath = "src/texts";
        File directory = new File(directoryPath);

        List<String> keywords = Arrays.asList(
                "has", "important", "taken"
        );

        long startTime = System.currentTimeMillis();
        Map<String, Map<String, Integer>> results = DocumentSearcher.searchKeywordsInDirectory(directory, keywords);
        long endTime = System.currentTimeMillis();
        System.out.println("Час: " + (endTime - startTime) + " мс");

        outputResults(results);
    }

    private static void outputResults(Map<String, Map<String, Integer>> results) {
        for (String keyword : results.keySet()) {
            Map<String, Integer> fileCounts = results.get(keyword);
            int totalCount = fileCounts.values().stream().mapToInt(Integer::intValue).sum();

            System.out.printf("\nСлово \"%s\" - загальна кількість входжень: %d%n",
                    keyword, totalCount);
            System.out.println("Знайдено у файлах:");

            if (fileCounts.isEmpty()) {
                System.out.println("  Не знайдено жодного входження");
            } else {
                fileCounts.entrySet()
                        .forEach(entry -> {
                            System.out.printf("  %s: %d входжень%n", entry.getKey(), entry.getValue());
                        });
            }
        }
    }
}