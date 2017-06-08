import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import spring.constructors.DriverData;

import java.io.File;

@ContextConfiguration(locations={"/spring-config.xml"})
public class TestRunner extends AbstractTestNGSpringContextTests {

    protected static final Logger LOG = LoggerFactory.getLogger(TestRunner.class);
    protected ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private final String AUT_PATH = "https://www.";
    private final boolean adBlockEnabled;

    public TestRunner(boolean adBlock){
        adBlockEnabled = adBlock;
    }

    @Autowired
    @Qualifier("driver")
    public DriverData driverData;

    @BeforeMethod
    public void init() {
        LOG.info("TestRunner.Before method: adBlockEnabled: " + adBlockEnabled);
        threadLocalDriver.set(initWebDriver(driverData.getBrowser(), adBlockEnabled));
        threadLocalDriver.get().get("https://getadblock.com/installed");
        threadLocalDriver.get().navigate().refresh();
        threadLocalDriver.get().get(AUT_PATH + driverData.getAutBaseURL() + "/");
    }

    @AfterMethod(alwaysRun = true)
    public void webDriverTearDown() {
        threadLocalDriver.get().quit();
    }

    private WebDriver initWebDriver(String browser, boolean adBlockEnabled) {
        WebDriver driver;
        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                if (adBlockEnabled) options.addExtensions(new File(System.getProperty("user.dir") + "\\driver\\adblock-3.10.0.crx"));
                //options.addArguments("load-extension=" + System.getProperty("user.dir") + "\\driver\\3.10.0_0");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                // For windows 'chromedriver.exe' for unix 'chromedriver'
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\driver\\chromedriver.exe");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "iexplore":
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\driver\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
//            case "phantomjs":
//                driver = new PhantomJSDriver();
//                break;
            default:
                throw new RuntimeException("Unsupported broser [" + browser + "] exception");
        }
        return driver;
    }
}
