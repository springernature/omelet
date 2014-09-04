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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/***
 * Implementation of IBrowser takes Browser Data and make BrowserObject based on
 * the hierarchy CommandLine->XML -> {@link VerifyBrowserValues}
 * 
 * @author kapilA
 * 
 */
public class BrowserConfiguration implements IBrowserConf {

	private Map<String, String> browerData;
	private List<String> bsURLs = new ArrayList<String>();
	private Map<BrowserConstant, String> mappedValues = new HashMap<BrowserConstant, String>();
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger
			.getLogger(BrowserConfiguration.class);

	public BrowserConfiguration(Map<String, String> browserData) {
		this.browerData = browserData;
		initializeMap();
	}

	public BrowserConfiguration() {
		initializeMap();
	}

	public String getBrowser() {
		return mappedValues.get(BrowserConstant.browserName);

	}

	public String getBrowserVersion() {

		return mappedValues.get(BrowserConstant.browserVersion);

	}

	public String getOsName() {

		return mappedValues.get(BrowserConstant.os);

	}

	public String getOsVersion() {
		return mappedValues.get(BrowserConstant.osVersion);
	}

	public boolean isBrowserStackSwitch() {
		return Boolean.valueOf(mappedValues.get(BrowserConstant.bsSwitch));
	}

	public String getBsUserName() {
		return mappedValues.get(BrowserConstant.bs_userName);
	}

	public String getBsPassword() {
		return mappedValues.get(BrowserConstant.bs_key);
	}

	public boolean isRemoteFlag() {
		return Boolean.valueOf(mappedValues.get(BrowserConstant.remoteFlag));
	}

	public String getRemoteURL() {
		return mappedValues.get(BrowserConstant.remoteURL);
	}

	public Integer getDriverTimeOut() {
		return Integer.valueOf(mappedValues.get(BrowserConstant.driverTimeOut));
	}

	public Integer getRetryFailedTestCaseCount() {
		return Integer.valueOf(mappedValues
				.get(BrowserConstant.retryFailedTestCase));
	}

	public boolean isBsLocalTesting() {
		return Boolean.valueOf(mappedValues
				.get(BrowserConstant.bs_localTesting));
	}

	public List<String> getBsURLs() {
		if (bsURLs.isEmpty()) {
			String url = mappedValues.get(BrowserConstant.bs_urls);
			if (url.contains(";")) {
				String[] urls = url.split(";");
				for (int i = 0; i < urls.length; i++) {
					this.bsURLs.add(urls[i].trim());
				}
			} else {
				this.bsURLs.add(url);
			}
		}
		return bsURLs;
	}

	public String getDevice() {
		return mappedValues.get(BrowserConstant.device);
	}

	public String getPlatform() {
		return mappedValues.get(BrowserConstant.platform);
	}

	public String getLocalIEServerPath() {

		return mappedValues.get(BrowserConstant.ieServerPath);
	}

	public String getLocalChromeServerPath() {
		return mappedValues.get(BrowserConstant.chromeServerPath);
	}

	public boolean isHighLightElementFlag() {

		return Boolean.valueOf(mappedValues
				.get(BrowserConstant.highlightElementFlag));
	}

	public boolean isScreenShotFlag() {
		return Boolean
				.valueOf(mappedValues.get(BrowserConstant.screenShotFlag));
	}

	public boolean isMobileTest() {
		return Boolean.valueOf(mappedValues.get(BrowserConstant.mobileTest));
	}

	private void initializeMap() {

		for (BrowserConstant b : BrowserConstant.values()) {
			mappedValues.put(b, getValue(b.toString()));
		}
		// After all values are set do a simple verification on all keys
		VerifyBrowserValues vbv = new VerifyBrowserValues(mappedValues);
		mappedValues = vbv.verifiedValues();
	}

	/***
	 * Main logic for setting the values
	 * @param key
	 * @return
	 */
	private String getValue(String key) {

		// Check Command Line if yes set the value to commandLine
		String sysVariable = System.getProperty(key);

		if (sysVariable != null && !StringUtils.isBlank(sysVariable))
			return sysVariable;
		// Check XML Configuration
		// CHeck of browserData is null for this
		if (browerData != null) {
			if (browerData.get(key) != null
					&& !StringUtils.isBlank(browerData.get(key))) {
				return browerData.get(key);
			}
		}
		// FallBack Mechanism for initializing value
		return null;
	}

	/***
	 * Implementing toString as Html reports will look good
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Browser:" + "<span style='font-weight:normal'>"
				+ getBrowser() + "</span>");

		if (isRemoteFlag()) {

			if (isBrowserStackSwitch()) {
				sb.append(",BrowserVersion:"
						+ "<span style='font-weight:normal'>"
						+ getBrowserVersion() + "</span>");
				sb.append(",OsName:" + "<span style='font-weight:normal'>"
						+ getOsName() + "</span>");
				sb.append(",OsVersion:" + "<span style='font-weight:normal'>"
						+ getOsVersion() + "</span>");
				sb.append(",BrowserStackKey:"
						+ "<span style='font-weight:normal'>" + getBsPassword()
						+ "</span>");
				sb.append(",BrowerStackSwitch:"
						+ "<span style='font-weight:normal'>"
						+ isBrowserStackSwitch() + "</span>");
				if (isBsLocalTesting())
					sb.append(",BrowserStack LocalTesting:"
							+ "<span style='font-weight:normal'>"
							+ isBsLocalTesting() + "</span>");
			}

			sb.append(",RemoteFlag:" + "<span style='font-weight:normal'>"
					+ isRemoteFlag() + "</span>");
			sb.append(",RemoteURL:" + "span style='font-weight:normal'>"
					+ getRemoteURL() + "</span>");
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {

		int hash = 7;

		if (this.isRemoteFlag() == true) {
			if (this.isBrowserStackSwitch() == true) {
				hash = 31 * hash + this.getBrowser().hashCode();
				hash = 31 * hash + this.getBrowserVersion().hashCode();
				hash = 31 * hash + this.getOsName().hashCode();
				hash = 31 * hash + this.getOsVersion().hashCode();
			} else {
				hash = 31 * hash + this.getBrowser().hashCode();
			}
		} else {
			hash = 31 * hash + this.getBrowser().hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || (obj.getClass() != this.getClass()))
			return false;
		BrowserConfiguration secondObj = (BrowserConfiguration) obj;

		if (this.isRemoteFlag() == secondObj.isRemoteFlag()) {
			if (this.isRemoteFlag() == true) {
				/*
				 * return this.getBrowser() == secondObj.getBrowser(); else{
				 */
				if (this.isBrowserStackSwitch() == secondObj
						.isBrowserStackSwitch()) {
					if (this.isBrowserStackSwitch() == true) {
						return (this.getBrowser() == secondObj.getBrowser()
								&& this.getBrowserVersion() == secondObj
										.getBrowserVersion()
								&& this.getOsName() == secondObj.getOsName() && this
									.getOsVersion() == secondObj.getOsVersion());
					} else {
						return (this.getBrowser() == secondObj.getBrowser());
					}
				} else {
					return false;
				}
			} else {
				return this.getBrowser() == secondObj.getBrowser();
			}
		}
		return false;

	}

}
