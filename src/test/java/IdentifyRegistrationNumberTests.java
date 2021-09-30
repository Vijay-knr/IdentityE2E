import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class IdentifyRegistrationNumberTests {
    private FindRegistrationNumberHelper findRegistrationNumberHelper;

    @Before
    public void setUp() {
        findRegistrationNumberHelper = new FindRegistrationNumberHelper();
    }

    @Test
    public void noMatchingNumberFound() {
        String text = "This text doesn't contain any car registration numbers";
        assertEquals(Collections.emptyList(), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void findARegWithNoWhiteSpacesAfterTwoDigits() {
        String text = "Checking example BMW with registration DN09HRM the value of the car is roughly around £3000.";
        assertEquals(asList("DN09HRM"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void findARegWithWhiteSpacesBetweenAfterTwoDigits() {
        String text = "However car with registration BW57 BOW is not worth much in current market.";
        assertEquals(asList("BW57BOW"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void stripWhiteSpacesFromReg() {
        String text = "However car with registration BW57 BOW is not worth much in current market.";
        assertEquals(asList("BW57BOW"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void textWithMultipleRegistrationNumbers() {
        String text = "There are multiple cars available higher than £10k with registraions KT17DLX and SG18 HTN.";
        assertEquals(asList("KT17DLX", "SG18HTN"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void textWithMultipleRegistrationNumbersFromMultipleLines() {
        String text = "However car with registration BW57 BOW is not worth much in current market. \n" +
                "There are multiple cars available higher than £10k with registraions KT17DLX and SG18 HTN.";
        assertEquals(asList("BW57BOW", "KT17DLX", "SG18HTN"), findRegistrationNumberHelper.find(text));
    }

    @Test
    public void inputFileToContainRegistrationNumbers() throws IOException {
        String inputFileContent = FileReader.getFileContent();
        assertEquals(
                asList("DN09HRM", "BW57BOW", "KT17DLX", "SG18HTN"),
                findRegistrationNumberHelper.find(inputFileContent)
        );
    }

    @Test
    public void test2() throws IOException {
        List<String> inputRegList = findRegistrationNumberHelper.find(FileReader.getFileContent());
        List<String> outputLines = FileReader.readOutputFiLeLinesExcludingHeader();
        for (String reg : inputRegList) {
            String actual = new WebsiteHelper().getCarDetails(reg);
            verifyInEachOutputLine(outputLines, reg, actual);
        }
    }

    private void verifyInEachOutputLine(List<String> outputLines, String reg, String actual) {
        for (String expectedLine : outputLines) {
            if (expectedLine.split(",")[0].equals(reg)) {
                printStatus(reg, actual, expectedLine);
            }
        }
    }

    private void printStatus(String reg, String actual, String expectedLine) {
        System.out.println("=====================================================================");
        System.out.println(reg + ": " + (expectedLine.equals(actual) ? "MATCHED" : "NOT MATCHED"));
        System.out.println("=====================================================================");
    }
}
