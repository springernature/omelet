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

import org.testng.annotations.BeforeMethod;

import com.springer.omelet.data.DriverConfigurations;

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
		browserValues.put(DriverConfigurations.LocalEnvironmentConfig.browserName.toString(), browserName);
		browserValues.put(DriverConfigurations.BrowserStackConfig.browserVersion.toString(),
				browserVersion);
		browserValues.put(DriverConfigurations.BrowserStackConfig.bs_key.toString(), bs_key);
		browserValues.put(DriverConfigurations.BrowserStackConfig.bs_localTesting.toString(),
				bs_localTesting);
		browserValues.put(DriverConfigurations.BrowserStackConfig.bs_urls.toString(), browserStackURLS);
		browserValues.put(DriverConfigurations.BrowserStackConfig.bs_userName.toString(), bs_user);
		browserValues.put(DriverConfigurations.BrowserStackConfig.bsSwitch.toString(),
				browserStackSwitch);
		browserValues.put(DriverConfigurations.LocalEnvironmentConfig.chromeServerPath.toString(),
				chromePath);
		browserValues.put(DriverConfigurations.BrowserStackConfig.device.toString(), device);
		browserValues.put(DriverConfigurations.FrameworkConfig.driverTimeOut.toString(),
				drivertimeout);
		browserValues.put(DriverConfigurations.FrameworkConfig.highlightElementFlag.toString(),
				highlightElement);
		browserValues.put(DriverConfigurations.LocalEnvironmentConfig.ieServerPath.toString(), ie_path);
		browserValues.put(DriverConfigurations.BrowserStackConfig.mobileTest.toString(), bs_mobileTest);
		browserValues.put(DriverConfigurations.BrowserStackConfig.os.toString(), osName);
		browserValues.put(DriverConfigurations.BrowserStackConfig.osVersion.toString(), osVersion);
		browserValues.put(DriverConfigurations.BrowserStackConfig.platform.toString(), platform);
		browserValues.put(DriverConfigurations.FrameworkConfig.remoteFlag.toString(), remoteFlag);
		browserValues
				.put(DriverConfigurations.FrameworkConfig.screenShotFlag.toString(), screenShot);
		browserValues.put(DriverConfigurations.HubConfig.remoteURL.toString(), reomteURL);
		browserValues.put(DriverConfigurations.FrameworkConfig.retryFailedTestCase.toString(),
				retryFailedTestCount);

	}

	@BeforeMethod
	public void cleanUp() {
		System.setProperty(DriverConfigurations.LocalEnvironmentConfig.browserName.toString(), "");
		System.setProperty(DriverConfigurations.FrameworkConfig.remoteFlag.toString(), "");
	}

	

}
