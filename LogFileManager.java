import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This class writes the valid hexadecimal values to a text file at the end.
 

public class LogFileManager {

    public static void writeSortedHexLog(List<String> values) {

        // This will Copy the values so the original list is not changed
        List<String> sortedValues = new ArrayList<>(values);

        // This will Sort using the actual hexadecimal value, not alphabetical order
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
