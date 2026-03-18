import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogFileManager {

    public static void writeSortedHexLog(List<String> values) {
        List<String> sortedValues = new ArrayList<>(values);

        sortedValues.sort((a, b) ->
                Integer.compare(HexConverter.hexToDecimal(a), HexConverter.hexToDecimal(b)));

        File file = new File("task9_hex_log.txt");

        try (FileWriter writer = new FileWriter(file)) {
            for (String value : sortedValues) {
                writer.write(value);
                writer.write(System.lineSeparator());
            }

            System.out.println("Log file saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing log file: " + e.getMessage());
        }
    }
}
