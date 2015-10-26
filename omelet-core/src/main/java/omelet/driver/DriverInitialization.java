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
import omelet.configuration.LoadCustomProperties;
import omelet.data.DataSource;
import omelet.data.IMethodContext;
import omelet.data.driverconf.PrepareDriverConf;
import omelet.testng.support.HtmlTable;
import omelet.testng.support.MethodContextCollection;
import omelet.testng.support.SAssert;
import org.apache.log4j.Logger;
import org.testng.*;

import java.io.File;
import java.sql.Driver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/***
 * This class initialize the thread local variable, clean up WebDriver
 * instances, and Report Generation, Screen shot etc
 *
 * @author kapilA
 */
public class DriverInitialization implements IInvokedMethodListener {

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
	protected void cleanupDriver() {
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
}
