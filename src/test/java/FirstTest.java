import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import page.objects.HomePage;
import page.objects.ResultsContainer;

public class FirstTest extends TestRunner {

    protected static final Logger LOG = LoggerFactory.getLogger(FirstTest.class);
    private boolean enableAdBlock = true;

    public FirstTest() {
        super(true);
    }

    @Test
    public void openPageWithAdBlock() {
        LOG.debug("Open page with adBlock : " + Thread.currentThread().getId());
        HomePage homePage = new HomePage(threadLocalDriver.get(), driverData);

        ResultsContainer searchResultContainer = homePage.search(driverData.getRequest());
        searchResultContainer.getResList(enableAdBlock);
    }
}