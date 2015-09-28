package omelet.support.saucelabs;

import omelet.data.xml.MappingParserRevisit;
import omelet.driver.DriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;

import java.util.Map;

/**
 * methods are used for reporting stuff in saucelabs
 * (RemoteDriver is not used for erxecuting tests)
 */
public class SauceLabsIntegration implements IInvokedMethodListener, ISuiteListener {
    private static final Logger LOGGER = Logger
            .getLogger(SauceLabsIntegration.class);

    private SauceLabsRestData slRestDataSingleRun;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        LOGGER.info("DriverManager.getBrowserConf().host().contains(\"sauce\"): "+DriverManager.getBrowserConf().host().contains("sauce"));
        LOGGER.info("DriverManager.getBrowserConf().isRemoteFlag(): "+DriverManager.getBrowserConf().isRemoteFlag());

        if (!DriverManager.getParallelMode().equals("false")) {
            try {
                // it will throw Null pointer exception for the method who are not
                // omelet driven , should be fixed as using try catch is a temporary
                // one!
                if (DriverManager.getBrowserConf().host().contains("sauce")
                        && DriverManager.getBrowserConf().isRemoteFlag()) {
                    RemoteWebDriver driver = (RemoteWebDriver) DriverManager.getDriver();
                    LOGGER.debug("After in SL Integration driver Session ID: "
                            + driver.getSessionId());

                    SauceLabsRestData slRestData = new SauceLabsRestData();
                    slRestData.setProjectName(MappingParserRevisit.getProjectName());
                    slRestData.setUser(DriverManager.getBrowserConf().getuserName());
                    slRestData.setPassword(DriverManager.getBrowserConf().getKey());
                    slRestData.setUserPass(slRestData.getUser() + ":" + slRestData.getPassword());

                    WebInterface slWebInterface = new WebInterface();
                    slWebInterface.updateSauceLabsJob(slRestData, method.getTestMethod().getMethodName(),
                            method.getTestResult().isSuccess());

                    Reporter.setCurrentTestResult(testResult);
                    Reporter.log("SauceLabs Report :: ");
                    Reporter.log(slWebInterface.generateLinkForJob(driver
                            .getSessionId().toString()));
                    Reporter.log("<br>");
                }
            } catch (Exception e) {
                LOGGER.error("Not to interfere in the test result!, but needs to be taken care!");
            }
        } else {
            if (DriverManager.getBrowserConf().host().contains("sauce")
                    && DriverManager.getBrowserConf().isRemoteFlag()) {
                RemoteWebDriver driver = (RemoteWebDriver) DriverManager.getDriver();
                this.slRestDataSingleRun = new SauceLabsRestData();
                this.slRestDataSingleRun.setJobID(driver.getSessionId().toString());
                this.slRestDataSingleRun.setProjectName(MappingParserRevisit.getProjectName());
                this.slRestDataSingleRun.setUser(DriverManager.getBrowserConf().getuserName());
                this.slRestDataSingleRun.setPassword(DriverManager.getBrowserConf().getKey());
                this.slRestDataSingleRun.setUserPass(this.slRestDataSingleRun.getUser() + ":" + this.slRestDataSingleRun.getPassword());
            }
        }
    }

    @Override
    public void onStart(ISuite suite) {

    }

    @Override
    public void onFinish(ISuite suite) {
        Map<String, ISuiteResult> results = suite.getResults();

        for (ISuiteResult result : results.values()) {
            for (ITestResult test : result.getTestContext().getPassedTests().getAllResults()) {
//                System.out.println("Driver: "+ DriverManager.getDriverWithoutInstance());
                System.out.println("Success Method: " + test.getName());
                System.out.println("Success: " + test.isSuccess());
            }
            for (ITestResult test : result.getTestContext().getFailedTests().getAllResults()) {
                System.out.println("Failed Method: " + test.getName());
                System.out.println("Failed: " + test.isSuccess());
            }
            for (ITestNGMethod test : result.getTestContext().getAllTestMethods()) {
                System.out.println("Method: " + test.getMethodName());
            }
        }
        LOGGER.info("on Finish");
        if (this.slRestDataSingleRun.getJobID() != null) {
            LOGGER.info("Stop Job in on Finish");
            WebInterface slWebInterface = new WebInterface();
            slWebInterface.stopJob(this.slRestDataSingleRun);
            slWebInterface.updateSauceLabsJob(this.slRestDataSingleRun, "test", true);
        }
    }
}
