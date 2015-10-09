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

import omelet.data.driverconf.IBrowserConf;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/***
 * DriverManager factory Class for Returning DriverManager Instances
 *
 * @author kapilA
 */
class DriverFactory {
	private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);

	private boolean remoteFlag;
	private String browser;
	private String host;
	private String port;
	private int driverTimeOut;
	private String USERNAME;
	private String AUTOMATE_KEY;
	private IBrowserConf browsConf;
	private String ieServerPath;
	private String chromeServerPath;
	private boolean ishiglightElementFlag;
	private DesiredCapabilities dc;
	private WebDriver webDriver = null;
	private String parallelMode = null;

	public DriverFactory(IBrowserConf browserConf, String parallelMode) {
		this.browsConf = browserConf;
		this.dc = browserConf.getCapabilities();
		this.browser = browserConf.getBrowser();
		this.remoteFlag = browsConf.isRemoteFlag();
		this.host = browserConf.host();
		this.port = browserConf.port();
		this.driverTimeOut = browserConf.getDriverTimeOut();
		this.USERNAME = browsConf.getuserName();
		this.AUTOMATE_KEY = browsConf.getKey();
		this.ieServerPath = browserConf.getLocalIEServerPath();
		this.chromeServerPath = browserConf.getLocalChromeServerPath();
		this.ishiglightElementFlag = browserConf.isHighLightElementFlag();
		this.parallelMode = parallelMode;
	}

	/***
	 * Return WebDriver either Remote/BrowserStack or local Browser based on
	 * remoteFlag
	 *
	 * @return
	 */
	public WebDriver intializeDriver() {
		if (remoteFlag) {
			if (!parallelMode.equals("false")) {
				webDriver = returnRemoteDriver();
			} else {
				if (webDriver == null) {
					webDriver = returnRemoteDriver();
				}
			}
		} else if (browser.toLowerCase().startsWith("f")) {
			LOGGER.debug("Returning firefox driver-Without Remote.");
			if (!parallelMode.equals("false")) {
				webDriver = new FirefoxDriver(dc);
			} else {
				if (webDriver == null) {
					webDriver = new FirefoxDriver(dc);
				}
			}
		} else if (browser.toLowerCase().startsWith("i")) {
			System.setProperty("webdriver.ie.driver", ieServerPath);
			LOGGER.debug("Returning ie driver-Without Remote.");
			if (!parallelMode.equals("false")) {
				webDriver = new InternetExplorerDriver(dc);
			} else {
				if (webDriver == null) {
					webDriver = new InternetExplorerDriver(dc);
				}
			}
		} else if (browser.toLowerCase().startsWith("c")) {
			System.setProperty("webdriver.chrome.driver", chromeServerPath);
			LOGGER.debug("Returning chrome driver-Without Remote.");
			if (!parallelMode.equals("false")) {
				webDriver = new ChromeDriver(dc);
			} else {
				if (webDriver == null) {
					webDriver = new ChromeDriver(dc);
				}
			}
		} else if (browser.toLowerCase().startsWith("h")) {
			LOGGER.debug("Browser is HTMLUNIT");
			if (!parallelMode.equals("false")) {
				webDriver = new HtmlUnitDriver(dc);
			} else {
				if (webDriver == null) {
					webDriver = new HtmlUnitDriver(dc);
				}
			}
		}
		setDriverTimeout();

		setHighlightForBrowserClicks();
		return webDriver;
	}

	private void setHighlightForBrowserClicks() {
		if (ishiglightElementFlag) {
			EventFiringWebDriver efw = new EventFiringWebDriver(webDriver);
			efw.register(new MyWebDriverListner());
			webDriver = efw;
		}
	}

	private void setDriverTimeout() {
		// For set driver timeout
		if (webDriver != null) {
			webDriver.manage().timeouts()
					 .implicitlyWait(driverTimeOut, TimeUnit.SECONDS);
		}
	}

	private WebDriver returnRemoteDriver() {
		if (StringUtils.isBlank(dc.getBrowserName())) {
			dc.setBrowserName(browser);
		}
		String remoteUrl;
		if (host.contains("browserstack") || host.contains("sauce")
				|| host.contains("testingbot")) {
			remoteUrl = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@"
					+ host + ":" + port + "/wd/hub";
		} else {
			remoteUrl = "http://" + host + ":" + port + "/wd/hub";
		}
		try {
			RemoteWebDriver driver = new RemoteWebDriver(
					new URL(remoteUrl), dc);

			// set local file detector for uploading file
			driver.setFileDetector(new LocalFileDetector());

			return driver;
		} catch (MalformedURLException e) {
			LOGGER.error(e);
			return null;
		}
	}
}