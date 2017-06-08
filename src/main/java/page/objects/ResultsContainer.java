package page.objects;

import core.WebDriverUtils;
import core.helper.JavaSoundRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import spring.constructors.DriverData;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsContainer {

    private WebDriver driver;
    private DriverData driverCnf;
    private final By RESULTS = By.cssSelector("div#results");
    private final By LIST_RES = By.cssSelector("ol.section-list>li>ol>li h3.yt-lockup-title >a");
    protected WebDriverWait wait;
    protected WebDriverUtils utils;

    public ResultsContainer(WebDriver driver, DriverData driverConfig) {
        this.driver = driver;
        driverCnf = driverConfig;
        wait = new WebDriverWait(driver, driverConfig.getTimeout());
        utils = new WebDriverUtils(driver, wait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(RESULTS));
    }

    public List<String> getResList(boolean enableAdBlock) {
        List<String> res = utils.getElements(LIST_RES).stream().limit(10).map(el -> el.getAttribute("href")).collect(Collectors.toList());
        String abblock;
        if (enableAdBlock) {
            abblock = "_adblock-on";
        } else abblock = "_adblock-off";

        res.forEach(a -> {
            System.out.println(a);
            driver.get(a);
            JavaSoundRecorder.grepSound(a.split("=")[1] + abblock);
        });
        return res.stream().map(a -> a.split("=")[1]).collect(Collectors.toList());
    }
}
