/*******************************************************************************
 *
 * 	Copyright 2014 Springer Science+Business Media Deutschland GmbH
 * 	
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 * 	you may not use this file except in compliance with the License.
 * 	You may obtain a copy of the License at
 * 	
 * 	    http://www.apache.org/licenses/LICENSE-2.0
 * 	
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 *******************************************************************************/

package com.springer.omelet.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

import com.springer.omelet.browserstacktunnel.BrowserStackTunnel;
import com.springer.omelet.data.IBrowserConf;
import com.springer.omelet.data.MappingParser;

/***
 * Driver factory Class for Returning Driver Instances
 * 
 * @author kapilA
 * 
 */
class DriverFactory {
	private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);

	private boolean remoteFlag;
	private String browser;
	private String remoteURL;
	private int driverTimeOut;
	private DesiredCapabilities dc;
	private String browser_version;
	private String os_name;
	private String os_version;
	private boolean browserStackSwitch;
	private String USERNAME;
	private String AUTOMATE_KEY;
	private boolean isBSLocalTesting;
	private List<String> bsURLS;
	private IBrowserConf browsConf;
	private String device;
	private String platform;
	private String ieServerPath;
	private String chromeServerPath;
	private boolean ishiglightElementFlag;
	private boolean isMobileTest;
	WebDriver webDriver = null;

	public DriverFactory(IBrowserConf browserConf) {
		this.browsConf = browserConf;
		this.browser = browsConf.getBrowser();
		this.browser_version = browserConf.getBrowserVersion();
		this.remoteFlag = browsConf.isRemoteFlag();
		this.remoteURL = browsConf.getRemoteURL();
		this.driverTimeOut = browserConf.getDriverTimeOut();
		this.os_name = browserConf.getOsName();
		this.os_version = browserConf.getOsVersion();
		this.browserStackSwitch = browsConf.isBrowserStackSwitch();
		this.USERNAME = browsConf.getBsUserName();
		this.AUTOMATE_KEY = browsConf.getBsPassword();
		this.isBSLocalTesting = browsConf.isBsLocalTesting();
		this.bsURLS = browsConf.getBsURLs();
		this.device = browserConf.getDevice();
		this.platform = browserConf.getPlatform();
		this.ieServerPath = browserConf.getLocalIEServerPath();
		this.chromeServerPath = browserConf.getLocalChromeServerPath();
		this.ishiglightElementFlag = browserConf.isHighLightElementFlag();
		this.isMobileTest = browserConf.isMobileTest();
		dc = new DesiredCapabilities();
	}

	/***
	 * Return WebDriver either Remote/BrowserStack or local Browser based on
	 * remoteFlag
	 * 
	 * @return
	 */
	public WebDriver intializeDriver() {

		if (remoteFlag) {
			RemoteBrowser rb = this.new RemoteBrowser();
			webDriver = rb.returnRemoteDriver();
		} else if (browser.toLowerCase().startsWith("f")) {
			LOGGER.debug("Returning firefox driver-Without Remote.");
			webDriver = new FirefoxDriver();
		} else if (browser.toLowerCase().startsWith("i")) {
			System.setProperty("webdriver.ie.driver", ieServerPath);
			LOGGER.debug("Returning ie driver-Without Remote.");
			webDriver = new InternetExplorerDriver();
		} else if (browser.toLowerCase().startsWith("c")) {
			System.setProperty("webdriver.chrome.driver", chromeServerPath);
			LOGGER.debug("Returning chrome driver-Without Remote.");
			webDriver = new ChromeDriver();
		} else if (browser.toLowerCase().startsWith("h")) {
			LOGGER.info("Browser is HTMLUNIT");
			webDriver = new HtmlUnitDriver();
		}

		// For maximizing driver windows and wait
		if (webDriver != null) {
			if (this.isMobileTest == false) {
				webDriver.manage().window().maximize();	
			}
			webDriver.manage().timeouts()
					.implicitlyWait(driverTimeOut, TimeUnit.SECONDS);
		}

		if (ishiglightElementFlag) {
			EventFiringWebDriver efw = new EventFiringWebDriver(webDriver);
			efw.register(new MyWebDriverListner());
			webDriver = efw;
		}
		return webDriver;

	}

	/***
	 * This class return Remote Driver either for Hub or BrowserStack
	 * 
	 * @author kapilA
	 * 
	 */
	private class RemoteBrowser {
		private BrowserStackTunnel bs;

		public RemoteBrowser() {

			setDesiredCapability();
		}

		public void setUpTunnel() {

			bs = BrowserStackTunnel.getInstance();
			bs.createTunnel(AUTOMATE_KEY, bsURLS);
		}

		public WebDriver returnRemoteDriver() {

			String c_remoteURL;
			String browserStackURL = "http://" + USERNAME + ":" + AUTOMATE_KEY
					+ "@hub.browserstack.com/wd/hub";
			if (browserStackSwitch) {
				c_remoteURL = browserStackURL;
				// check if tunnel needs to be setup
				if (isBSLocalTesting)
					setUpTunnel();
			} else {
				c_remoteURL = remoteURL;
			}

			try {
				RemoteWebDriver driver = new RemoteWebDriver(new URL(
						c_remoteURL), dc);
				// set local file detector for uploading file
				driver.setFileDetector(new LocalFileDetector());
				return driver;
			} catch (MalformedURLException e) {
				LOGGER.error(e);
				return null;
			}

		}

		private void setDesiredCapability() {

			if (browserStackSwitch) {

				dc.setCapability("project", MappingParser.getInstance()
						.getProjectName());
				if (MappingParser.getInstance().getBuildNumber() != null) {
					dc.setCapability("build", MappingParser.getInstance()
							.getBuildNumber());
				}
				dc.setCapability("platform", platform);
				dc.setCapability("acceptSslCerts", "true");
				if (isMobileTest) {
					if (platform.toLowerCase().contains("android")) {
						dc.setCapability("device", device);
						dc.setCapability("browserName", browser);
					} else if (platform.toLowerCase().contains("mac")) {
						dc.setCapability("device", device);
						dc.setCapability("browserName", browser);
					}
				} else {
					dc.setCapability("browser", browser);
					dc.setCapability("browser_version", browser_version);
					dc.setCapability("os", os_name);
					dc.setCapability("osVersion", os_version);

				}
				if (isBSLocalTesting) {

					dc.setCapability("browserstack.tunnel", "true");
					dc.setCapability("browserstack.tunnelIdentifier",
							AUTOMATE_KEY);
				}

				dc.setCapability("browserTimeout", "200");
				dc.setCapability("browserstack.debug", "true");
			}

			else {
				if (browser.toLowerCase().startsWith("f")) {
					dc = DesiredCapabilities.firefox();
				} else if (browser.toLowerCase().startsWith("i")) {
					dc = DesiredCapabilities.internetExplorer();

				} else if (browser.toLowerCase().startsWith("c")) {
					dc = DesiredCapabilities.chrome();
				}
			}
		}

	}
}