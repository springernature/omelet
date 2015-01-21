package omelet.support.saucelabs;

import omelet.driver.Driver;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class SauceLabsIntegration implements IInvokedMethodListener {
	private static final Logger LOGGER = Logger
			.getLogger(SauceLabsIntegration.class);

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (Driver.getBrowserConf().host().contains("sauce")) {
			RemoteWebDriver driver = (RemoteWebDriver) Driver.getDriver();
			LOGGER.debug("After in SL Integration driver Session ID: " + driver.getSessionId());
			WebInterface slWebInterface = new WebInterface();
			slWebInterface.updateSauceLabsJob(driver.getSessionId().toString(),
					method.getTestMethod().getMethodName(), method
							.getTestResult().isSuccess());
			
			Reporter.setCurrentTestResult(testResult);
			Reporter.log("SauceLabs Report :: ");
			Reporter.log(slWebInterface.generateLinkForJob(driver
					.getSessionId().toString()));
			Reporter.log(slWebInterface.generateLinkForEmbedScript(driver
					.getSessionId().toString(), true));
//			Reporter.log("<br>");
//			 Reporter.log("SauceLabs Video :: "
//			 + slWebInterface.generateLinkForEmbedScript(driver
//			 .getSessionId().toString(), false));
			Reporter.log("<br>");
		}
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		
	}

}
