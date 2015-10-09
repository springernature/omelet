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

import omelet.configuration.DefaultBrowserConf;
import omelet.data.driverconf.IBrowserConf;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/***
 * DriverManager class which returns Webdriver specific to configuration
 *
 * @author kapilA
 */
public class DriverManager {

	private static final Logger LOGGER = Logger.getLogger(DriverManager.class);
	protected static InheritableThreadLocal<IBrowserConf> browserConf = new InheritableThreadLocal<IBrowserConf>();
	protected static InheritableThreadLocal<WebDriver> driver = new InheritableThreadLocal<WebDriver>();
	protected static DriverFactory driverFactory;
	protected static String parallelMode;

	private DriverManager() {
	}

	/***
	 * sets the driver in ThreadLocal
	 */
	protected static void setDriverValue() {
		browserConf.set(DefaultBrowserConf.get());
		DriverFactory df = new DriverFactory(DefaultBrowserConf.get(), parallelMode);
		driver.set(df.intializeDriver());
	}

	protected static void setDriverValue(IBrowserConf b_conf) {
		browserConf.set(b_conf);
		if (driverFactory == null) {
			driverFactory = new DriverFactory(b_conf, parallelMode);
		}
		driver.set(driverFactory.intializeDriver());
	}

	public static String getParallelMode() {
		return parallelMode;
	}

	/***
	 * Fetch browserConfiguration
	 *
	 * @return {@link IBrowserConf}
	 */
	public static IBrowserConf getBrowserConf() {
		return browserConf.get();
	}

	public static WebDriver getSetDriver() {
		if (getDriver() != null) {
			return getDriver();
		} else {
			return createDriver();
		}
	}

	public static WebDriver getSetDriver(IBrowserConf browserConf) {
		if (getDriver() != null) {
			return getDriver();
		} else {
			return createDriver(browserConf);
		}
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static WebDriver createDriver() {
		setDriverValue();
		return getDriver();
	}

	public static WebDriver createDriver(IBrowserConf browserConf) {
		setDriverValue(browserConf);
		return getDriver();
	}

	/***
	 * This method checks if driver present yes then quit else ignore
	 */
	protected static void tearDown() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
			driverFactory = null;
		} else if (driver != null) {
			driver.remove();
			driverFactory = null;
		}
	}

	protected static boolean driverRemovedStatus() {

		if (driver.get() == null) {
			return true;
		}
		return false;
	}
}