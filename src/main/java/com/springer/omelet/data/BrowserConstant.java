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

/***
 * Keys which can be set from CommandLine , in any property file ,XML etc
 * 
 * @author kapilA
 * 
 */

public enum BrowserConstant {
	device(""), browserName("FireFox"), browserVersion(""), os(""), osVersion(
			""), bsSwitch("false"), bs_userName(""), bs_key(""), remoteFlag(
			"false"), remoteURL(""), driverTimeOut("30"), bs_localTesting(
			"false"), bs_urls(""), platform(""), retryFailedTestCase("0"), ieServerPath(
			""), chromeServerPath(""), highlightElementFlag("false"), screenShotFlag(
			"true"), mobileTest("false");
	private String defaultValue;

	BrowserConstant(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String get() {
		return this.defaultValue;
	}
}
