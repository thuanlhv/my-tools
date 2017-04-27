package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class DateExtractor {
    private List<GdcDate> dates;

    DateExtractor(Path baselinePath) throws IOException {
        loadBaseline(baselinePath);
    }

    private void loadBaseline(Path baselinePath) throws IOException {
        dates = Files.lines(baselinePath).map(s -> {
            String[] parts = s.split(",");
            return new GdcDate(LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])), Integer.parseInt(parts[3]));

        }).collect(Collectors.toList());
    }

    List<GdcDate> extractHashFile(Path hashFilePath) throws IOException {
        if (dates.isEmpty() || dates == null)
            throw new RuntimeException("Date baseline has not been loaded !@#");

        List<GdcDate> results = new ArrayList<>();

        Files.readAllLines(hashFilePath).stream()
                .map(Integer::parseInt)
                .forEach(line -> results.add(
                        dates.stream()
                                .filter(date -> date.getHash() == line)
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Can't find values having hash " +
                                        line))));

        return results;
    }
}
