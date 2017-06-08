import core.helper.CompareWaveFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import page.objects.HomePage;
import page.objects.ResultsContainer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SecondTest extends TestRunner {

    protected static final Logger LOG = LoggerFactory.getLogger(SecondTest.class);
    private boolean enableAdBlock = false;
    private List<String> listWavFiles;

    public SecondTest() {
        super(false);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void webDriverTearDown() {
        super.webDriverTearDown();
        List<String> res = listWavFiles.stream().map(file -> CompareWaveFiles.getSimilarity(file)).collect(Collectors.toList());
        saveRes2CSV(res);
    }

    @Test
    public void openPageWithoutAdBlock() {
        LOG.debug("Open page without adBlock : " + Thread.currentThread().getId());
        HomePage homePage = new HomePage(threadLocalDriver.get(), driverData);

        ResultsContainer searchResultContainer = homePage.search(driverData.getRequest());
        listWavFiles = searchResultContainer.getResList(enableAdBlock);
    }

    private void saveRes2CSV(List<String> res) {
        String csvFile = System.getProperty("user.dir") + "\\target\\" + "result.csv";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(csvFile);
            for (String record : res) {
                fileWriter.append(record);
                fileWriter.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                LOG.error("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
}