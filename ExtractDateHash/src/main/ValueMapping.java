package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueMapping {
    public static void main(String[] args) {
        if (args.length != 3)
            throw new IllegalArgumentException("Following args are required \n 1st arg: baseline file, it must " +
                    "have this format key;value \n 2nd arg: input file which is needed to be replaced \n 3rd arg: " +
                    "output");

        String baseline = args[0];
        String input = args[1];
        String output = args[2];

        try {
            replaceValues(Paths.get(input), Paths.get(output), readBaseline(Paths.get(baseline)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<String, String> readBaseline(Path path) throws IOException {
        HashMap baseline = new HashMap<>();
        Files.readAllLines(path).forEach(line -> {
            String[] parts = line.split(";");
            baseline.put(parts[0], parts[1]);
        });
        return baseline;
    }

    private static void replaceValues(Path source, Path output, HashMap<String, String> baseline) throws
            IOException {
        List<String> content = Files.readAllLines(source);
        List<String> replacedContent = new ArrayList<>();

        content.stream().forEachOrdered(line -> {
            boolean foundFlag = false;
            String[] lineParts = line.split(";");
            for (Map.Entry<String, String> entry : baseline.entrySet()) {
                for (int i = 0; i < lineParts.length; i++) {
                    if (lineParts[i].equals(entry.getKey())) {
                        lineParts[i] = entry.getValue();
                        foundFlag = true;
                    }
                }
                if (foundFlag) break;
            }
            replacedContent.add(String.join(";", lineParts));
        });

        Files.write(output, replacedContent);
    }
}
