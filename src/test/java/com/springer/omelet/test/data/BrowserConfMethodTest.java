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
package com.springer.omelet.test.data;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.springer.omelet.common.Utils;
import com.springer.omelet.data.BrowserConfiguration;
import com.springer.omelet.data.BrowserStackConstant;
import com.springer.omelet.data.IProperty;
import com.springer.omelet.data.PropertyMapping;

public class BrowserConfMethodTest {

	// If values are sent via command Line then we should get CommandLine Values
	// If no command Line and all Map values then values return by method should
	// return Map Values
	// If no Map values no commandLine then Framework Properties value should
	// return
	// if Some commandLine and all MappedValues then commandLine takes
	// preference and other by Mapped Values and rest by Framework Properties
	String browserName = "Chrome";
	String browserVersion = "11";
	String osName = "Windows";
	String osVersion = "XP";
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
	IProperty prop = new PropertyMapping(Utils.getResources(this,
			"Framework.properties"));

	@BeforeMethod
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

	@Test(description = "All Values set by HashMap")
	public void verifyMapValues() {

		// Map<String,String> browserValues = new HashMap<String, String>();

		BrowserConfiguration bc = new BrowserConfiguration(browserValues);
		Assert.assertEquals(bc.getBrowser(), browserName);
		Assert.assertEquals(bc.getBrowserVersion(), browserVersion);
		Assert.assertEquals(bc.getBsPassword(), bs_key);
		Assert.assertEquals(bc.getBsUserName(), bs_user);
		Assert.assertEquals(bc.getDevice(), device);
		Assert.assertEquals(bc.getLocalChromeServerPath(), chromePath);
		Assert.assertEquals(bc.getOsName(), osName);
		String[] bs_URLS = browserStackURLS.split(";");
		for (int i = 0; i < bs_URLS.length; i++) {
			Assert.assertEquals(bc.getBsURLs().get(i).trim(), bs_URLS[i].trim());
		}

		// Assert.assertEquals(bc.getBsURLs(), browserStackURLS.split(";"));
		Assert.assertEquals(bc.getOsVersion(), osVersion);
		Assert.assertEquals(bc.getPlatform(), platform);
		Assert.assertEquals(bc.getRemoteURL(), reomteURL);
		Assert.assertEquals(bc.getDriverTimeOut(),
				Integer.valueOf(drivertimeout));
		Assert.assertEquals(bc.getRetryFailedTestCaseCount(),
				Integer.valueOf(retryFailedTestCount));
		Assert.assertTrue(bc.isBrowserStackSwitch() == Boolean
				.valueOf(browserStackSwitch));
		// Assert.assertEquals(bc.isBrowserStackSwitch(),
		// Boolean.valueOf(browserStackSwitch));
		Assert.assertTrue(bc.isBsLocalTesting() == Boolean
				.valueOf(bs_localTesting));

		Assert.assertTrue(bc.isHighLightElementFlag() == Boolean
				.valueOf(highlightElement));
		Assert.assertTrue(bc.isMobileTest() == Boolean.valueOf(bs_mobileTest));
		Assert.assertTrue(bc.isRemoteFlag() == Boolean.valueOf(remoteFlag));
		Assert.assertTrue(bc.isScreenShotFlag() == Boolean.valueOf(screenShot));

	}

	@Test(dependsOnMethods = "verifyMapValues")
	public void verifyMapValueEmpty() {
		browserValues.put(BrowserStackConstant.browserName.toString(), "    ");
		BrowserConfiguration bc = new BrowserConfiguration(browserValues);
		Assert.assertEquals(bc.getBrowser(),
				prop.getValue(BrowserStackConstant.browserName));
	}

	@Test(dependsOnMethods = "verifyMapValueEmpty", description = "Some Values by cmd and all by map")
	public void verifyCmd_Map_Mix() {
		System.setProperty(BrowserStackConstant.browserName.toString(), "firefox");
		BrowserConfiguration bc = new BrowserConfiguration(browserValues);
		Assert.assertEquals(bc.getBrowser(), "firefox");
		Assert.assertEquals(bc.getOsVersion(), osVersion);
		Assert.assertEquals(bc.getPlatform(), platform);
		Assert.assertEquals(bc.getRemoteURL(), reomteURL);
	}

	@Test(dependsOnMethods = "verifyCmd_Map_Mix", description = "cmd empty  ")
	public void verifyCmdEmpty() {

		System.setProperty(BrowserStackConstant.browserName.toString(), "");
		// osVersion = null;
		// System.setProperty(BrowserConstant.os.toString(),null);
		BrowserConfiguration bc = new BrowserConfiguration(browserValues);
		Assert.assertEquals(bc.getBrowser(), browserName);
		Assert.assertEquals(bc.getOsVersion(), osVersion);
		Assert.assertEquals(bc.getPlatform(), platform);
		Assert.assertEquals(bc.getRemoteURL(), reomteURL);
		Assert.assertEquals(bc.getOsName(), osName);
	}

	// TODO
	public void emptyConstructor() {
		// take example from verifyBrowserVal_Test
		// All the default values provided in framework properties should be
		// given
	}

}
