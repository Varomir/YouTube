package page.objects;

import core.WebDriverUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import spring.constructors.DriverData;

public class HomePage {

    private WebDriver driver;
    private DriverData driverCnf;
    protected int timeout;
    protected WebDriverWait wait;
    protected WebDriverUtils utils;
    private static final By ROOT = By.cssSelector("input[name='search_query']");

    public HomePage(WebDriver driver, DriverData driverConfig){
        this.driver = driver;
        driverCnf = driverConfig;
        timeout = driverConfig.getTimeout();
        wait = new WebDriverWait(driver, timeout);
        utils = new WebDriverUtils(driver, wait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ROOT));
    }

    public ResultsContainer search(String text) {
        utils.getElement(ROOT).sendKeys(Keys.chord(text), Keys.ENTER);
        return new ResultsContainer(driver, driverCnf);
    }
}