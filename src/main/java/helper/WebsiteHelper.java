package helper;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

public class WebsiteHelper {
    private WebDriver driver;
    private String resultFormat = "%s,%s,%s,%s,%s";
    public static final String CAR_TAX_CHECK_WEBSITE = "https://cartaxcheck.co.uk/";
    public static final String REG_INPUT = "vrm-input";
    public static final String BUTTON_TEXT_FREE_CAR_CHECK = "//button[text()='Free Car Check']";
    public static final String IDENTIFIER = "//dt[text()='%s']//following-sibling::dd";

    public String getCarDetails(String reg) {
        visit(CAR_TAX_CHECK_WEBSITE);
        search(reg);
        String result = String.format(
                resultFormat,
                read("Registration"),
                read("Make"),
                read("Model"),
                read("Colour"),
                read("Year")
        );
        driver.quit();
        return result;
    }

    private void search(String reg) {
        driver.findElement(By.id(REG_INPUT)).sendKeys(reg);
        driver.findElement(By.xpath(BUTTON_TEXT_FREE_CAR_CHECK)).click();
        waitUntilElementToHave(getElement("Registration"), reg);
    }

    private void visit(String carTaxCheckWebsite) {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(carTaxCheckWebsite);
    }

    private String read(String label) {
        return getElement(label).getText();
    }

    private void waitUntilElementToHave(WebElement element, String reg) {
        FluentWait wait = new FluentWait(driver);
        wait.withTimeout(5000, TimeUnit.MILLISECONDS);
        wait.pollingEvery(250, TimeUnit.MILLISECONDS);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, reg));
        } catch (TimeoutException ignored) {
            System.out.println("=====================================================================");
            System.out.println(reg + ": Vehicle Not Found");
            System.out.println("=====================================================================");
        }
    }
    private WebElement getElement(String label) {
        return driver.findElement(By.xpath(String.format(IDENTIFIER, label)));
    }
}
