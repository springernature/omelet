package com.springer.omelet.test.data.validateBrowsers;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.springer.omelet.data.DriverConfigurations;
import com.springer.omelet.data.driverconf.ValidateBrowserRules;
import com.springer.omelet.exception.FrameworkException;

/**
 * Test methods to test the BrowserStack config rules.
 * @author borz01
 *
 */
public class BrowserStackRuleTest {
	Map<String, String> browserMap;

	@BeforeMethod
	public void setup() {
		browserMap = new HashMap<String, String>();
		browserMap.put(
				DriverConfigurations.FrameworkConfig.remoteFlag.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.bsSwitch.toString(),
				"True");
	}

	@Test
	public void verifyIfMobileIsTrueBrowserIsIphone() {
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"iPhone");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.device.toString(),
				"iPhone");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.platform.toString(),
				"MAC");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test
	public void verifyIfMobileIsTrueBrowserIsIpad() {
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"iPad");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.device.toString(),
				"iPad mini Retina");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.platform.toString(),
				"MAC");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test
	public void verifyIfMobileIsTrueBrowserIsAndroid() {
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"android");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.device.toString(),
				"Samsung Galaxy S5");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.platform.toString(),
				"android");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfMobileIsTrueBrowserIsWrong() {
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.device.toString(),
				"Samsung Galaxy S5");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.platform.toString(),
				"android");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfMobileIsTrueAndDeviceIsEmpty() {
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.device.toString(), "");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.platform.toString(),
				"android");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfMobileIsTrueAndPlatformIsEmpty() {
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.device.toString(),
				"test");
		browserMap
				.put(DriverConfigurations.BrowserStackConfig.platform
						.toString(), "");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfRemoteUrlIsEmpty() {
		browserMap.put(DriverConfigurations.BrowserStackConfig.bs_localTesting
				.toString(), "True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.bs_urls.toString(), "");

		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"android");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.device.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.platform.toString(),
				"test");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test
	public void verifyIfRemoteUrlIsSet() {
		browserMap.put(DriverConfigurations.BrowserStackConfig.bs_localTesting
				.toString(), "True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.bs_urls.toString(),
				"test");

		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"android");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.device.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.platform.toString(),
				"test");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfMobileIsFalseAndAllEmpty() {
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"False");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"");
		browserMap.put(DriverConfigurations.BrowserStackConfig.browserVersion
				.toString(), "");
		browserMap.put(DriverConfigurations.BrowserStackConfig.os.toString(),
				"");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.osVersion.toString(),
				"");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test
	public void verifyIfMobileIsFalseAndAllIsSet() {
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.mobileTest.toString(),
				"False");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.browserName.toString(),
				"test");
		browserMap.put(DriverConfigurations.BrowserStackConfig.browserVersion
				.toString(), "test");
		browserMap.put(DriverConfigurations.BrowserStackConfig.os.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.BrowserStackConfig.osVersion.toString(),
				"test");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}
}
