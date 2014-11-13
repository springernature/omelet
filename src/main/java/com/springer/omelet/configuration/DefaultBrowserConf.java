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
package com.springer.omelet.configuration;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.springer.omelet.data.BrowserConfiguration;
import com.springer.omelet.data.BrowserConstant;
import com.springer.omelet.data.IProperty;
import com.springer.omelet.data.driverconf.IBrowserConf;

/***
 * This class set the variable required to configure Driver either from Command
 * Line or property file ,Command Line is given preference
 * 
 * @author kapilA
 * 
 */

public class DefaultBrowserConf {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger
			.getLogger(DefaultBrowserConf.class);
	private static IBrowserConf browserConf;
	private static IProperty customProp;

	/***
	 * hiding the constructor
	 */
	private DefaultBrowserConf() {

	}

	/***
	 * Return {@link IBrowserConf} depending on if {@link CustomBrowserConf} is used or 
	 * Default values set configured
	 * 
	 * @param browserConf
	 */
	public static IBrowserConf get() {
		// TODO: this browserConf is set once in complete execution , which
		// means if we do Driver.getDriver() multiple times then we get the same
		// conf
		if (browserConf == null)
			synchronized (DefaultBrowserConf.class) {

				if (browserConf == null) {
					setEscapePropertyForReportNG();
					if (customProp == null)
						browserConf = new BrowserConfiguration();
					else
						browserConf = new BrowserConfiguration(getKeyValue());
				}
			}
		return browserConf;
	}

	/****
	 * Check if custom properties file present or not if, present initialize
	 * customProp to prop file
	 * 
	 * @return: true or false
	 */
	protected static void loadCustomPropertiesFile(IProperty prop) {
		// Delete the browserConf
		// Set Custom property
		browserConf = null;
		customProp = prop;

	}

	private static HashMap<String, String> getKeyValue() {
		HashMap<String, String> f_map = new HashMap<String, String>();
		for (BrowserConstant b : BrowserConstant.values()) {
			f_map.put(b.toString(), customProp.getValue(b));
		}
		return f_map;
	}

	private static void setEscapePropertyForReportNG() {
		System.setProperty("org.uncommons.reportng.coverage-report", "true");
		final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
		System.setProperty(ESCAPE_PROPERTY, "false");
	}

}
