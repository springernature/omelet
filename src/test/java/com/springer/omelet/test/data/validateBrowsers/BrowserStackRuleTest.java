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
	/*Map<String, String> browserMap;

	@BeforeMethod
	public void setup() {
		browserMap = new HashMap<String, String>();
		browserMap.put(
				DriverConfigurations.FrameworkConfig.remoteFlag.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.CloudConfig.bsSwitch.toString(),
				"True");
	}

	@Test
	public void verifyIfMobileIsTrueBrowserIsIphone() {
		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"iPhone");
		browserMap.put(
				DriverConfigurations.CloudConfig.device.toString(),
				"iPhone");
		browserMap.put(
				DriverConfigurations.CloudConfig.platform.toString(),
				"MAC");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test
	public void verifyIfMobileIsTrueBrowserIsIpad() {
		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"iPad");
		browserMap.put(
				DriverConfigurations.CloudConfig.device.toString(),
				"iPad mini Retina");
		browserMap.put(
				DriverConfigurations.CloudConfig.platform.toString(),
				"MAC");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test
	public void verifyIfMobileIsTrueBrowserIsAndroid() {
		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"android");
		browserMap.put(
				DriverConfigurations.CloudConfig.device.toString(),
				"Samsung Galaxy S5");
		browserMap.put(
				DriverConfigurations.CloudConfig.platform.toString(),
				"android");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfMobileIsTrueBrowserIsWrong() {
		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.CloudConfig.device.toString(),
				"Samsung Galaxy S5");
		browserMap.put(
				DriverConfigurations.CloudConfig.platform.toString(),
				"android");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfMobileIsTrueAndDeviceIsEmpty() {
		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.CloudConfig.device.toString(), "");
		browserMap.put(
				DriverConfigurations.CloudConfig.platform.toString(),
				"android");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfMobileIsTrueAndPlatformIsEmpty() {
		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.CloudConfig.device.toString(),
				"test");
		browserMap
				.put(DriverConfigurations.CloudConfig.platform
						.toString(), "");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfRemoteUrlIsEmpty() {
		browserMap.put(DriverConfigurations.CloudConfig.bs_localTesting
				.toString(), "True");
		browserMap.put(
				DriverConfigurations.CloudConfig.bs_urls.toString(), "");

		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"android");
		browserMap.put(
				DriverConfigurations.CloudConfig.device.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.CloudConfig.platform.toString(),
				"test");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test
	public void verifyIfRemoteUrlIsSet() {
		browserMap.put(DriverConfigurations.CloudConfig.bs_localTesting
				.toString(), "True");
		browserMap.put(
				DriverConfigurations.CloudConfig.bs_urls.toString(),
				"test");

		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"True");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"android");
		browserMap.put(
				DriverConfigurations.CloudConfig.device.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.CloudConfig.platform.toString(),
				"test");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void verifyIfMobileIsFalseAndAllEmpty() {
		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"False");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"");
		browserMap.put(DriverConfigurations.CloudConfig.browserVersion
				.toString(), "");
		browserMap.put(DriverConfigurations.CloudConfig.os.toString(),
				"");
		browserMap.put(
				DriverConfigurations.CloudConfig.osVersion.toString(),
				"");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

	@Test
	public void verifyIfMobileIsFalseAndAllIsSet() {
		browserMap.put(
				DriverConfigurations.CloudConfig.mobileTest.toString(),
				"False");
		browserMap.put(
				DriverConfigurations.LocalEnvironmentConfig.browserName.toString(),
				"test");
		browserMap.put(DriverConfigurations.CloudConfig.browserVersion
				.toString(), "test");
		browserMap.put(DriverConfigurations.CloudConfig.os.toString(),
				"test");
		browserMap.put(
				DriverConfigurations.CloudConfig.osVersion.toString(),
				"test");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}*/
}
