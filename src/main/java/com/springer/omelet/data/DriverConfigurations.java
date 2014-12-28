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
public class DriverConfigurations {
	
	/**
	 * 
	 * @author borz01
	 *
	 */
	public enum FrameworkConfig {
		remoteFlag("false"), driverTimeOut("30"), retryFailedTestCase("0"), highlightElementFlag(
				"false"), screenShotFlag("true");
		private String defaultValue;

		FrameworkConfig(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String get() {
			return this.defaultValue;
		}

	}

	/**
	 * 
	 * @author borz01
	 *
	 */
	public enum BrowserStackConfig {
		device(""), browserVersion(""), os(""), osVersion(
				""), bsSwitch("false"), bs_userName(""), bs_key(""), bs_localTesting(
				"false"), bs_urls(""), platform(""), mobileTest("false");
		private String defaultValue;

		BrowserStackConfig(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String get() {
			return this.defaultValue;
		}
	}

	/**
	 * 
	 * @author borz01
	 *
	 */
	public enum LocalEnvironmentConfig {
		browserName("FireFox"), ieServerPath(""), chromeServerPath("");
		private String defaultValue;

		LocalEnvironmentConfig(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String get() {
			return this.defaultValue;
		}

	}

	/**
	 * 
	 * @author borz01
	 *
	 */
	public enum HubConfig {
		remoteURL("");
		private String defaultValue;

		HubConfig(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String get() {
			return this.defaultValue;
		}

	}
}
