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

package omelet.driver;

import omelet.common.Utils;
import omelet.data.DataSource;
import omelet.data.IMethodContext;
import omelet.data.driverconf.PrepareDriverConf;
import omelet.testng.support.HtmlTable;
import omelet.testng.support.MethodContextCollection;
import omelet.testng.support.SAssert;
import org.apache.log4j.Logger;
import org.testng.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/***
 * This class initialize the thread local variable, clean up WebDriver
 * instances, and Report Generation, Screen shot etc
 *
 * @author kapilA
 */
public class DriverInitialization implements IInvokedMethodListener, ISuiteListener {

	private static final Logger LOGGER = Logger
			.getLogger(DriverInitialization.class);
	// output dir of TestNg currently being used in SAssert
	public static String outPutDir;

	/***
	 * This Method Set the driver if @BeforeMethod Configuration present if not
	 * then set the driver for @Test Methods
	 *
	 * @author kapilA
	 */
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// Setting the output directory
		if (outPutDir == null) {
			outPutDir = testResult.getTestContext().getOutputDirectory();
		}
	}

	/***
	 * Quits DriverManager and generate table report, if @BeforeMethod is not present
	 * and @AfterMethod is present in TestClass then DriverManager.getDriver() in after
	 * method will be null
	 *
	 * @author kapilA
	 */
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			IMethodContext methodContext = MethodContextCollection.getMethodContext(
					Utils.getFullMethodName(method.getTestMethod().getConstructorOrMethod().getMethod()));
			boolean beforeMethodPresent = methodContext.isBeforeMethod();
			boolean afterMethodShouldbePresent = methodContext.isAfterMethod();

			publishHtmlTable(testResult);
			addScreenShot(testResult);

			if (beforeMethodPresent) {
				if (!afterMethodShouldbePresent) {
					cleanupTest(method, testResult);
				}
			} else {
				cleanupTest(method, testResult);
			}
		}

		if (!DriverManager.getBrowserConf().getDataSource().equals(DataSource.XmlSuiteData.toString())) {
			if (DriverManager.getDriver() != null) {
				cleanupDriver();
			}
		}
	}


	/***
	 * Clearing AssertMap in Soft Assert, Cleaning BrowserStack
	 * Tunnel if at all present
	 *
	 * @param method
	 * @param testResult
	 * @author kapilA
	 */
	private void cleanupTest(IInvokedMethod method, ITestResult testResult) {
		try {
			Reporter.setCurrentTestResult(testResult);
		} catch (Exception e) {
			LOGGER.error(
					"Catching Exception in After Invocation So test Result are not altered due to it",
					e);
		} finally {
			SAssert.m_errors.get().clear();
			SAssert.assertMap.get().clear();
		}
	}

	/***
	 * DriverManager quit
	 * Tunnel if at all present
	 *
	 * @author abeg01
	 */
	private void cleanupDriver() {
		try {
			if (!DriverManager.driverRemovedStatus()) {
				DriverManager.tearDown();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Catching Exception in After Invocation So test Result are not altered due to it",
					e);
		} finally {
			if (!DriverManager.driverRemovedStatus()) {
				DriverManager.tearDown();
			}
		}
	}


	/***
	 * Adds HTML Table with data in the output report
	 *
	 * @param testResult
	 */
	private void publishHtmlTable(ITestResult testResult) {
		try {
			Reporter.setCurrentTestResult(testResult);
			HtmlTable report = new HtmlTable(SAssert.assertMap.get(),
											 testResult.getName());
			Reporter.log("Table Report is:::" + report.getTable());
		} catch (Exception e) {
			LOGGER.error("Catching exception in public HTML Method", e);
		}
	}

	/***
	 * Add the screen shot to test case due to throwable exception other than
	 * Soft Assert
	 *
	 * @param testResult
	 * @author kapilA
	 */
	private void addScreenShot(ITestResult testResult) {
		try {
			Reporter.setCurrentTestResult(testResult);
			if (DriverManager.getBrowserConf().isScreenShotFlag()) {
				if (testResult.getThrowable() != null) {
					String throwMessage = (testResult.getThrowable()
													 .getMessage() != null) ? testResult.getThrowable()
																						.getMessage() : "";
					if (!throwMessage.contains("asserts failed")) {
						String screenShotName = UUID.randomUUID().toString() + ".png";
						String outPutDirectory = testResult.getTestContext().getOutputDirectory();
						String filePath = outPutDirectory + File.separator + screenShotName;
						DriverUtility.takeScreenShot(DriverManager.driver.get(), filePath);
						// Append the screen Shot in the Reporter Log
						Reporter.log("Test Case -" + testResult.getName()
											 + " failed due to exception screen shot below");
						Reporter.log("<div style=\"height:400px; width: 750px; overflow:scroll\"><img src=\""
											 + "../" + SuiteConfiguration.suiteName
											 + "/" + screenShotName + "\"></div>");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Catching exception in add screen shot Method", e);
		}
	}

	/***
	 * This Method Set the driver
	 *
	 * @param iSuite
	 * @author abeg01
	 */
	@Override
	public void onStart(ISuite iSuite) {
		LOGGER.debug("Setting the WebDriver in Before Suite");
		if (DriverManager.driver != null) {
			LOGGER.debug("DriverManager.driver: " + DriverManager.driver);
			cleanupDriver();
			DriverManager.tearDown();
		}
		// Initializing browser so that will be same across all the child
		// threads
		DriverManager.browserConf.set(null);
		DriverManager.parallelMode = iSuite.getParallel();

		Map<String, String> map = new HashMap<String, String>();
		map.put("browsername", iSuite.getParameter("browsername"));
		map.put("dc.platform", iSuite.getParameter("dc.platform"));
		map.put("dc.version", iSuite.getParameter("dc.version"));

		PrepareDriverConf pdc = new PrepareDriverConf(map);
		pdc.refineBrowserValues();
		DriverManager.browserConf.set(pdc.get());
		LOGGER.debug("DriverManager.getBrowserConf(): " + DriverManager.getBrowserConf());

		// need as otherwise will produce unexpected output
		SAssert.m_errors.get();
		SAssert.assertMap.get();
	}

	/***
	 * This Method quites the driver
	 *
	 * @param iSuite
	 * @author abeg01
	 */
	@Override
	public void onFinish(ISuite iSuite) {
		// Check for AfterMethod if present check for browser and quit
		LOGGER.debug("onFinish DriverManager.getDriver(): " + DriverManager.getDriver());

		if (DriverManager.getBrowserConf().getDataSource().equals(DataSource.XmlSuiteData.toString())) {
			if (DriverManager.getDriver() != null) {
				cleanupDriver();
			}
		}
	}
}
