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
package com.springer.omelet.test.browserup;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.springer.omelet.data.BrowserConfiguration;
import com.springer.omelet.data.BrowserStackConstant;
import com.springer.omelet.driver.Driver;

public class SimpleTest {

	String browserName = "chrome";
	String browserVersion = "11";
	String osName = "Windows";
	String osVersion = "XP";
	// Remote Flag is false
	String remoteFlag = "false";
	String reomteURL = "testRemoteURL";
	String browserStackSwitch = "true";
	String bs_key = "testbskey";
	String bs_user = "testusName";
	String retryFailedTestCount = "2";
	String browserStackURLS = "https:testURL1;https:testURL3";
	String bs_localTesting = "false";
	String bs_mobileTest = "true";
	String drivertimeout = "10";
	String device = "IPAD";
	String ie_path = "testiepath";
	String chromePath = "testchromepath";
	String highlightElement = "false";
	String screenShot = "true";
	String platform = "testPlatform";

	Map<String, String> browserValues = new HashMap<String, String>();

	public void setup() {
		browserValues.clear();
		browserValues.put(BrowserStackConstant.browserName.toString(), browserName);
		browserValues.put(BrowserStackConstant.browserVersion.toString(),
				browserVersion);
		browserValues.put(BrowserStackConstant.bs_key.toString(), bs_key);
		browserValues.put(BrowserStackConstant.bs_localTesting.toString(),
				bs_localTesting);
		browserValues.put(BrowserStackConstant.bs_urls.toString(), browserStackURLS);
		browserValues.put(BrowserStackConstant.bs_userName.toString(), bs_user);
		browserValues.put(BrowserStackConstant.bsSwitch.toString(),
				browserStackSwitch);
		browserValues.put(BrowserStackConstant.chromeServerPath.toString(),
				chromePath);
		browserValues.put(BrowserStackConstant.device.toString(), device);
		browserValues.put(BrowserStackConstant.driverTimeOut.toString(),
				drivertimeout);
		browserValues.put(BrowserStackConstant.highlightElementFlag.toString(),
				highlightElement);
		browserValues.put(BrowserStackConstant.ieServerPath.toString(), ie_path);
		browserValues.put(BrowserStackConstant.mobileTest.toString(), bs_mobileTest);
		browserValues.put(BrowserStackConstant.os.toString(), osName);
		browserValues.put(BrowserStackConstant.osVersion.toString(), osVersion);
		browserValues.put(BrowserStackConstant.platform.toString(), platform);
		browserValues.put(BrowserStackConstant.remoteFlag.toString(), remoteFlag);
		browserValues
				.put(BrowserStackConstant.screenShotFlag.toString(), screenShot);
		browserValues.put(BrowserStackConstant.remoteURL.toString(), reomteURL);
		browserValues.put(BrowserStackConstant.retryFailedTestCase.toString(),
				retryFailedTestCount);

	}

	@BeforeMethod
	public void cleanUp() {
		System.setProperty(BrowserStackConstant.browserName.toString(), "");
		System.setProperty(BrowserStackConstant.remoteFlag.toString(), "");
	}

	@Test
	public void default_BrowserUP() {
		WebDriver driver = Driver.getDriver();
		Assert.assertEquals(driver.getClass().getName(),
				"org.openqa.selenium.firefox.FirefoxDriver");
	}

	// @Test
	public void chrome_BrowserUP() {
		// create
		setup();
		browserValues.put(BrowserStackConstant.chromeServerPath.toString(), "");
		WebDriver driver = Driver.getDriver(new BrowserConfiguration(
				browserValues));
		Assert.assertEquals(driver.getClass().getName(),
				"org.openqa.selenium.chrome.ChromeDriver");
	}

}
