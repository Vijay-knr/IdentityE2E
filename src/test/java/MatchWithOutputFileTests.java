import filereader.FileReader;
import filereader.FileReaderFactory;
import helper.FindRegistrationNumberHelper;
import helper.WebsiteHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class MatchWithOutputFileTests {
    private FileReader textFileReader;
    private FindRegistrationNumberHelper findRegistrationNumberHelper;

    @Before
    public void setup() {
        textFileReader = FileReaderFactory.provideFileReader("text");
        findRegistrationNumberHelper = new FindRegistrationNumberHelper();
    }

    @Test
    public void inputFileToContainRegistrationNumbers() throws IOException {

        String inputFileContent = textFileReader.getInputFileContent();
        assertEquals(
                asList("DN09HRM", "BW57BOW", "KT17DLX", "SG18HTN"),
                findRegistrationNumberHelper.find(inputFileContent)
        );
    }

    @Test
    public void compareWebsiteOutputWithOutputFIleContentsForMatching() throws IOException {
        textFileReader = FileReaderFactory.provideFileReader("text");
        List<String> inputRegList = findRegistrationNumberHelper.find(textFileReader.getInputFileContent());
        List<String> outputLines = textFileReader.getOutputFileContent();
        inputRegList
                .forEach(
                        reg -> {
                            String actual = new WebsiteHelper().getCarDetails(reg);
                            verifyInEachOutputLine(outputLines, reg, actual);
                        });
    }

    private void verifyInEachOutputLine(List<String> outputLines, String reg, String actual) {
        outputLines
                .stream()
                .filter(expectedLine -> expectedLine.split(",")[0].equals(reg))
                .forEach(expectedLine -> printStatus(reg, actual, expectedLine));
    }

    private void printStatus(String reg, String actual, String expectedLine) {
        System.out.println("=====================================================================");
        System.out.println(reg + ": " + (expectedLine.equals(actual) ? "MATCHED" : "NOT MATCHED"));
        System.out.println("=====================================================================");
    }
}
