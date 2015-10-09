/*******************************************************************************
 * Copyright 2014 Springer Science+Business Media Deutschland GmbH
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package omelet.support.saucelabs;

import omelet.data.xml.MappingParserRevisit;
import omelet.driver.DriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;

import java.util.Map;

/**
 * methods are used for reporting stuff in saucelabs
 * (RemoteDriver is not used for executing tests)
 */
public class SauceLabsIntegration implements IInvokedMethodListener, ISuiteListener {
	private static final Logger LOGGER = Logger
			.getLogger(SauceLabsIntegration.class);

	private SauceLabsRestData slRestDataSingleRun;

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (DriverManager.getDriver() != null) {
			DriverManager.getDriver().manage().deleteAllCookies();
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (!DriverManager.getParallelMode().equals("false")) {
			try {
				// it will throw Null pointer exception for the method who are not
				// omelet driven , should be fixed as using try catch is a temporary
				// one!
				if (DriverManager.getBrowserConf().host().contains("sauce")
						&& DriverManager.getBrowserConf().isRemoteFlag()) {
					RemoteWebDriver driver = (RemoteWebDriver) DriverManager.createDriver();
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

				RemoteWebDriver driver = (RemoteWebDriver) DriverManager.getSetDriver();

				this.slRestDataSingleRun = new SauceLabsRestData();
				this.slRestDataSingleRun.setJobID(driver.getSessionId().toString());
				this.slRestDataSingleRun.setProjectName(MappingParserRevisit.getProjectName());
				this.slRestDataSingleRun.setUser(DriverManager.getBrowserConf().getuserName());
				this.slRestDataSingleRun.setPassword(DriverManager.getBrowserConf().getKey());
				this.slRestDataSingleRun
						.setUserPass(this.slRestDataSingleRun.getUser() + ":" + this.slRestDataSingleRun.getPassword
								());
			}
		}
	}

	@Override
	public void onStart(ISuite suite) {
	}

	@Override
	public void onFinish(ISuite suite) {
		if (this.slRestDataSingleRun != null && slRestDataSingleRun.getJobID() != null) {
			Map<String, ISuiteResult> results = suite.getResults();
			Boolean testSuccess = true;
			StringBuilder testNames = new StringBuilder();

			for (ISuiteResult result : results.values()) {
				if (!result.getTestContext().getFailedTests().getAllResults().isEmpty()) {
					testSuccess = false;
				}
				for (ITestNGMethod test : result.getTestContext().getAllTestMethods()) {
					if (testNames.toString().isEmpty()) {
						testNames.append(test.getMethodName());
					} else {
						testNames.append(" , " + test.getMethodName());
					}
				}
			}

			WebInterface slWebInterface = new WebInterface();
			slWebInterface.stopJob(this.slRestDataSingleRun);
			slWebInterface.updateSauceLabsJob(this.slRestDataSingleRun, testNames.toString(), testSuccess);
			this.slRestDataSingleRun.setJobID(null);
		}
	}
}
