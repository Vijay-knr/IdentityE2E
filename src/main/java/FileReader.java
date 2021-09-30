import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    private static final String INPUT_FILE_PATH = "src/main/resources/car_input.txt";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/car_output.txt";

    public static List<String> readOutputFiLeLinesExcludingHeader() throws IOException {
        List<String> lines =  Files.readAllLines(Paths.get(OUTPUT_FILE_PATH), StandardCharsets.US_ASCII);
        lines.remove(0);
        return lines;
    }

    public static String getFileContent() throws IOException {
        return Files.readString(Paths.get(INPUT_FILE_PATH));
    }
}
