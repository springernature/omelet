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

import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/***
 * For Terminating BrowserStack tunnel after Suite has been executed
 *
 * @author kapilA
 *
 */
public class SuiteConfiguration implements ISuiteListener {

	private static final Logger LOGGER = Logger.getLogger(SuiteConfiguration.class);
	public static String suiteName;
	private static ISuite suite;


	@Override
	public void onStart(ISuite suite) {
		LOGGER.debug("DriverManager.driverFactory: " + DriverManager.driverFactory);
		LOGGER.debug("DriverManager.driver: " + DriverManager.driver);
		LOGGER.debug("DriverManager.getBrowserConf(): " + DriverManager.getBrowserConf());

		Logo.getInstance().printLogoAndVersion();
		suiteName = suite.getName();
		this.suite = suite;
		//ReportNG property
		System.setProperty("org.uncommons.reportng.coverage-report", "true");
		final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
		System.setProperty(ESCAPE_PROPERTY, "false");
	}

	@Override
	public void onFinish(ISuite suite) {
	}

	public static String getSuiteProperty(String key) {
		if (suite != null) {
			return suite.getParameter(key);
		}
		return null;
	}
}
