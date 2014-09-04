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
import org.testng.annotations.Test;

import com.springer.omelet.data.BrowserConstant;
import com.springer.omelet.data.VerifyBrowserValues;

public class VerifyBrowserValueTest {

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

	@Test
	public void checkDefaultOption() {
		// if no values are given thats is null or blank then default value are
		// set
		Map<BrowserConstant, String> testMap = new HashMap<BrowserConstant, String>();
		VerifyBrowserValues vb = new VerifyBrowserValues(testMap);
		vb.isFrameworkProperties("test");
		testMap = vb.verifiedValues();
		Assert.assertEquals(testMap.get(BrowserConstant.highlightElementFlag),
				highlightElementFlag);
		Assert.assertEquals(testMap.get(BrowserConstant.driverTimeOut),
				driverTimeout);
		Assert.assertEquals(testMap.get(BrowserConstant.retryFailedTestCase),
				retryFailTestCount);
		Assert.assertEquals(testMap.get(BrowserConstant.screenShotFlag),
				screenShotFlag);
		Assert.assertEquals(testMap.get(BrowserConstant.remoteFlag), remoteFlag);
		Assert.assertEquals(testMap.get(BrowserConstant.remoteURL), remotURL);
		Assert.assertEquals(testMap.get(BrowserConstant.ieServerPath), iePath);
		Assert.assertEquals(testMap.get(BrowserConstant.chromeServerPath),
				chromePathD);
		Assert.assertEquals(testMap.get(BrowserConstant.browserVersion),
				browserVersion);
		Assert.assertEquals(testMap.get(BrowserConstant.browserName),
				browserName);
		Assert.assertEquals(testMap.get(BrowserConstant.bsSwitch), bs_switch);
		Assert.assertEquals(testMap.get(BrowserConstant.bs_localTesting),
				bs_localTesting);
		Assert.assertEquals(testMap.get(BrowserConstant.mobileTest),
				bs_mobileTest);
		Assert.assertEquals(testMap.get(BrowserConstant.platform), bs_platform);
		Assert.assertEquals(testMap.get(BrowserConstant.os), bs_os);
		Assert.assertEquals(testMap.get(BrowserConstant.osVersion),
				bs_osversion);
		Assert.assertEquals(testMap.get(BrowserConstant.device), bs_device);
		Assert.assertEquals(testMap.get(BrowserConstant.bs_key), bs_key);
		Assert.assertEquals(testMap.get(BrowserConstant.bs_userName),
				bs_userName);
		Assert.assertEquals(testMap.get(BrowserConstant.bs_urls), bs_URLS);

	}

	public void allValuesInPropertiesFile() {
		// Create a testPropertyFile where are the properties are set
	}

	public void allValuesInProperties_someBlank() {

	}

	public void someValuesInHashMap_FrameworkPropAbsent() {

	}

	public void someValueInHashMap_someBlankInHashMap_someInFramework_someBlankInFramework() {

	}

	public void expectedException_remoteURL() {
		// remoteFlag on
		// Bs switch off
		// RemoteURL == null or empty both
	}

	public void expectException_chromeServerPath() {
		// remoteFlag off
		// BrowserName chrome
		// BS switch == off
		// chromeserverpath empty
	}

	public void expectException_ieServerPath() {
		// RemoteFlag off
		// BrowserName ie
		// bsswitch off
		// ieserverpath empty
	}

	public void expectException_browserVersion() {
		// RemoteFlag on
		// bsswitch on
		// browser version empty or null
	}

	public void expectException_device() {
		// RemoteFlag on
		// bsswitch on
		// mobile test on

	}

	public void expectException_bsKey() {
		// remoteFlag on
		// bsswitch on
		// bs_key null or empty
	}

	public void expectException_bsusername() {
		// remoteFlag on
		// bsswitch on
		// bs_username null or empty
	}

	public void expectException_bsURL() {
		// remoteFlag on
		// bsswitch on
		// bs_url null or empty
	}

}
