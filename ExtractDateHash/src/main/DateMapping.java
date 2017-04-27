package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class DateMapping {
    public static void main(String[] args) {
        if (args.length != 4)
            throw new IllegalArgumentException("Following args are required \n 1st arg: hash file path" +
                    "\n 2nd arg: baseline file path \n 3rd arg: output file \n 4th arg: expected date format");

        String hashFile = args[0];
        String baselineFile = args[1];
        String output = args[2];
        String format = args[3];

        try {
            DateExtractor extractor = new DateExtractor(Paths.get(baselineFile));

            exportExtractedDateToFile(Paths.get(output),
                    extractor.extractHashFile(Paths.get(hashFile)), DateTimeFormatter.ofPattern(format));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportExtractedDateToFile(Path path, List<GdcDate> dates, DateTimeFormatter format)
            throws IOException {
        List<String> lines = dates.stream().map(GdcDate::getDate)
                .map(date -> date.format(format)).collect(Collectors.toList());

        Files.write(path, lines);
    }
}
