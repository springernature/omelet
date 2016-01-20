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
import omelet.data.driverconf.PrepareDriverConf;
import omelet.testng.support.SAssert;
import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by borz01 on 26.10.15.
 */
public class DriverInitializationSuite extends DriverInitialization implements ISuiteListener {

	private static final Logger LOGGER = Logger
			.getLogger(DriverInitializationSuite.class);
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

		LoadCustomProperties customProperties = new LoadCustomProperties(Utils.getResources(DriverInitialization.class,
																							"BrowserDC.properties"));
		List<String> browserDCs = customProperties.getCustomProperties();

		Map<String, String> map = new HashMap<String, String>();
		for (String prop : browserDCs) {
			map.put(prop, iSuite.getParameter(prop));
		}

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
