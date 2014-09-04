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
package com.springer.omelet.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.springer.omelet.browserstacktunnel.OsCheck;
import com.springer.omelet.common.Utils;
import com.springer.omelet.exception.FrameworkException;

/***
 * Does the validation of BrowserConfiguration , set Default values if no value
 * is provided to it
 * 
 * @author kapil
 * 
 */
public class VerifyBrowserValues {

	private Map<BrowserConstant, String> browserConfMapValues = new HashMap<BrowserConstant, String>();
	private String fileName = "Framework.properties";

	private boolean isFrameworkProperties = false;
	private PropertyValueMin prop = null;

	// These are the default properties assumed by the code
	private String highlightElementFlag = "false";
	private String driverTimeout = "40";
	private String retryFailTestCount = "0";
	private String screenShotFlag = "true";

	private String remoteFlag = "false";
	private String remotURL = "";
	private String iePath = "";
	private String chromePathD = "";
	private String browserVersion = "";
	private String browserName = "firefox";
	private String bs_switch = "false";
	private String bs_localTesting = "false";
	private String bs_mobileTest = "false";
	private String bs_platform = "WINDOWS";
	private String bs_os = "WINDOWS";
	private String bs_osversion = "7";
	private String bs_device = "";
	private String bs_key = "";
	private String bs_userName = "";
	private String bs_URLS = "";
	private static Logger LOGGER = Logger.getLogger(VerifyBrowserValues.class);

	public VerifyBrowserValues(Map<BrowserConstant, String> mappValues) {
		this.browserConfMapValues.putAll(mappValues);
		// check FrameworkProperties
		isFrameworkProperties(fileName);
	}

	/***
	 * This is made public just for Testing
	 * 
	 * @param fileName
	 */
	public void isFrameworkProperties(String fileName) {
		if (Utils.getResources(VerifyBrowserValues.class, fileName) == null) {
			isFrameworkProperties = false;
		} else {
			isFrameworkProperties = true;
			prop = new PropertyValueMin(Utils.getResources(
					VerifyBrowserValues.class, fileName));
		}
	}

	public void setFileName(String fileN) {
		isFrameworkProperties(fileN);
	}

	/***
	 * Main method for verifying the data
	 * 
	 * @return
	 */
	public Map<BrowserConstant, String> verifiedValues() {
		// it should obey order for setting these values
		setValue(BrowserConstant.driverTimeOut);
		setValue(BrowserConstant.highlightElementFlag);
		setValue(BrowserConstant.screenShotFlag);
		setValue(BrowserConstant.retryFailedTestCase);

		setValue(BrowserConstant.remoteFlag);
		setValue(BrowserConstant.bsSwitch);
		setValue(BrowserConstant.browserName);
		setValue(BrowserConstant.ieServerPath);
		setValue(BrowserConstant.chromeServerPath);
		setValue(BrowserConstant.remoteURL);
		setValue(BrowserConstant.bs_localTesting);
		setValue(BrowserConstant.mobileTest);
		setValue(BrowserConstant.platform);
		setValue(BrowserConstant.device);
		setValue(BrowserConstant.bs_key);
		setValue(BrowserConstant.bs_userName);
		setValue(BrowserConstant.bs_urls);
		setValue(BrowserConstant.os);
		setValue(BrowserConstant.osVersion);
		setValue(BrowserConstant.browserVersion);

		return browserConfMapValues;

	}

	private String defaultIeServerPath() {
		// what is the value of the remote flag
		if ("true".equals(browserConfMapValues.get(BrowserConstant.remoteFlag))) {
			return iePath;
		} else {
			if (browserConfMapValues.get(BrowserConstant.browserName)
					.toLowerCase().startsWith("i")) {
				switch (OsCheck.getOS()) {

				case MAC:
					throw new FrameworkException(
							"Did you expecte to run IE on MAC?");
				case UNIX:
					throw new FrameworkException(
							"Did you expect to run IE on linux?");
				case WIN:
					// check if IEDriverServer.exe is present in the classpath
					String iepath = Utils.getResources(this,
							"IEDriverServer.exe");
					if (iepath != null) {
						return iepath;
					} else {
						throw new FrameworkException(
								"Please add IEDriverServer.exe in the class path, As there is no such file !");
					}
				default:
					throw new FrameworkException(
							"Not able to recognize operating system");
				}
			}
			return "";
		}
	}

	private String defaultChromeServerPath() {
		if ("true".equals(browserConfMapValues.get(BrowserConstant.remoteFlag))) {
			return chromePathD;
		} else {
			// TODO:Do we really require this >?
			if (browserConfMapValues.get(BrowserConstant.browserName)
					.toLowerCase().startsWith("c")) {
				String chromeBinaryPath = Utils.getResources(this,
						"chromedriver");
				if (chromeBinaryPath != null) {
					return chromeBinaryPath;
				} else {
					throw new FrameworkException(
							"Please add ChromeServer binary in classpath , There is no such file!");
				}
			}
			return chromePathD;
		}

	}

	private String defaultBrowserVersion() {
		if ("true".equals(browserConfMapValues.get(BrowserConstant.remoteFlag))
				&& "true".equals(browserConfMapValues
						.get(BrowserConstant.bsSwitch))) {
			throw new FrameworkException("Please set browser Version ");
		}
		return browserVersion;
	}

	private String defaultDevice() {
		if ("true".equals(browserConfMapValues.get(BrowserConstant.remoteFlag))
				&& "true".equals(browserConfMapValues
						.get(BrowserConstant.bsSwitch))
				&& "true".equals(browserConfMapValues
						.get(BrowserConstant.mobileTest))) {
			throw new FrameworkException("Please set Device name");
		} else
			return bs_device;
	}

	private String defaultRemoteURL() {
		if ("true".equals(browserConfMapValues.get(BrowserConstant.remoteFlag))
				&& !"true".equals(browserConfMapValues
						.get(BrowserConstant.bsSwitch))) {
			throw new FrameworkException("Please set remoteURL");
		} else
			return remotURL;
	};

	private String defaultBSKey() {
		if ("true".equals(browserConfMapValues.get(BrowserConstant.remoteFlag))
				&& "true".equals(browserConfMapValues
						.get(BrowserConstant.bsSwitch))) {
			throw new FrameworkException("Please set BrowserStack key");
		} else
			return bs_key;
	}

	private String defaultBSUserName() {
		if ("true".equals(browserConfMapValues.get(BrowserConstant.remoteFlag))
				&& "true".equals(browserConfMapValues
						.get(BrowserConstant.bsSwitch))) {
			throw new FrameworkException("Please set BrowserStack username");
		} else
			return bs_userName;
	}

	private String defaultBSURLS() {
		if ("true".equals(browserConfMapValues.get(BrowserConstant.remoteFlag))
				&& "true".equals(browserConfMapValues
						.get(BrowserConstant.bsSwitch))
				&& "true".equals(browserConfMapValues
						.get(BrowserConstant.bs_localTesting))) {
			throw new FrameworkException(
					"Please add BSURLS for local Testing on BrowserStack");
		} else {
			return bs_URLS;
		}
	}

	/***
	 * Check if default "Framework.properties" file is present? and then
	 * will check of the values or set to default values
	 * 
	 * @param key
	 */
	private void setValue(BrowserConstant key) {
		if (browserConfMapValues.get(key) == null
				|| StringUtils.isBlank(browserConfMapValues.get(key))) {
			// Check if framework propeties present ?
			if (isFrameworkProperties) {
				if (prop.getValue(key.toString()) == null
						|| StringUtils.isBlank(prop.getValue(key.toString()))) {
					defaultValueSet(key);
				} else {
					browserConfMapValues
							.put(key, prop.getValue(key.toString()));
				}
			} else {
				defaultValueSet(key);
			}
		}
	}

	/***
	 * This method assumes that every effort to check value from command line ,
	 * xml and frameworkpropeties have failed and let assign some default value
	 * or throw exception to user
	 * 
	 * @param key
	 */

	private void defaultValueSet(BrowserConstant key) {
		String logMessage = "As " + key.toString()
				+ " is not present setting the default value to:";
		switch (key) {
		case remoteFlag:
			LOGGER.debug(logMessage + remoteFlag);
			browserConfMapValues.put(key, remoteFlag);
			break;
		case browserName:
			LOGGER.debug(logMessage + browserName);
			browserConfMapValues.put(key, browserName);
			break;
		case platform:
			// TODO
			// Check for the mobile test if Mobile Test is true then which
			// platform to be by default
			LOGGER.debug(logMessage + bs_platform);
			browserConfMapValues.put(key, bs_platform);
			break;
		case bs_localTesting:
			LOGGER.debug(logMessage + bs_localTesting);
			browserConfMapValues.put(key, bs_localTesting);
			break;
		case ieServerPath:
			browserConfMapValues.put(key, defaultIeServerPath());
			break;
		case chromeServerPath:
			LOGGER.debug(logMessage);
			browserConfMapValues.put(key, defaultChromeServerPath());
			break;
		case mobileTest:
			LOGGER.debug(logMessage + bs_mobileTest);
			browserConfMapValues.put(key, bs_mobileTest);
			break;
		case browserVersion:
			browserConfMapValues.put(key, defaultBrowserVersion());
			break;
		case bsSwitch:
			LOGGER.debug(logMessage + bs_switch);
			browserConfMapValues.put(key, bs_switch);
			break;
		case os:
			LOGGER.debug(logMessage + bs_os);
			browserConfMapValues.put(key, bs_os);
			break;
		case osVersion:
			LOGGER.debug(logMessage + bs_osversion);
			browserConfMapValues.put(key, bs_osversion);
			break;
		case highlightElementFlag:
			LOGGER.debug(logMessage + highlightElementFlag);
			browserConfMapValues.put(key, highlightElementFlag);
			break;
		case driverTimeOut:
			LOGGER.debug(logMessage + driverTimeout);
			browserConfMapValues.put(key, driverTimeout);
			break;
		case retryFailedTestCase:
			LOGGER.debug(logMessage + retryFailTestCount);
			browserConfMapValues.put(key, retryFailTestCount);
			break;
		case screenShotFlag:
			LOGGER.debug(logMessage + screenShotFlag);
			browserConfMapValues.put(key, screenShotFlag);
			break;
		case device:
			browserConfMapValues.put(key, defaultDevice());
			break;
		case remoteURL:
			browserConfMapValues.put(key, defaultRemoteURL());
			break;
		case bs_key:
			browserConfMapValues.put(key, defaultBSKey());
			break;
		case bs_userName:
			browserConfMapValues.put(key, defaultBSUserName());
			break;
		case bs_urls:
			browserConfMapValues.put(key, defaultBSURLS());
			break;
		default:
			throw new FrameworkException(
					"This key:"
							+ key
							+ " doesnot have any case statement for default values ,kindly arrange the same");
		}

	}
}
