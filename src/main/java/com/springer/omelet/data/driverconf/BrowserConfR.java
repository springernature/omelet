package com.springer.omelet.data.driverconf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.springer.omelet.data.BrowserConstant;

public class BrowserConfR implements IBrowserConf {

	private Map<String, String> mappedValues;
	private List<String> bsURLs = new ArrayList<String>();

	public BrowserConfR(Map<String, String> completeBrowserMap) {
		mappedValues = completeBrowserMap;
	}

	public String getBrowser() {
		return mappedValues.get(BrowserConstant.browserName.toString());
	}

	public String getBrowserVersion() {
		return mappedValues.get(BrowserConstant.browserVersion.toString());
	}

	public String getOsName() {
		return mappedValues.get(BrowserConstant.os.toString());
	}

	public String getOsVersion() {
		return mappedValues.get(BrowserConstant.osVersion.toString());
	}

	public boolean isBrowserStackSwitch() {
		return Boolean.valueOf(mappedValues.get(BrowserConstant.bsSwitch
				.toString()));
	}

	public String getBsUserName() {
		return mappedValues.get(BrowserConstant.bs_userName.toString());
	}

	public String getBsPassword() {
		return mappedValues.get(BrowserConstant.bs_key.toString());
	}

	public boolean isRemoteFlag() {
		return Boolean.valueOf(mappedValues.get(BrowserConstant.remoteFlag
				.toString()));
	}

	public String getRemoteURL() {
		return mappedValues.get(BrowserConstant.remoteURL.toString());
	}

	public Integer getDriverTimeOut() {
		return Integer.valueOf(mappedValues.get(BrowserConstant.driverTimeOut
				.toString()));
	}

	public Integer getRetryFailedTestCaseCount() {
		return Integer.valueOf(mappedValues
				.get(BrowserConstant.retryFailedTestCase.toString()));
	}

	public boolean isBsLocalTesting() {
		return Boolean.valueOf(mappedValues.get(BrowserConstant.bs_localTesting
				.toString()));
	}

	public List<String> getBsURLs() {
		if (bsURLs.isEmpty()) {
			String url = mappedValues.get(BrowserConstant.bs_urls.toString());
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
		return mappedValues.get(BrowserConstant.device.toString());
	}

	public String getPlatform() {
		return mappedValues.get(BrowserConstant.platform.toString());
	}

	public String getLocalIEServerPath() {
		return mappedValues.get(BrowserConstant.ieServerPath.toString());
	}

	public String getLocalChromeServerPath() {
		return mappedValues.get(BrowserConstant.chromeServerPath.toString());
	}

	public boolean isHighLightElementFlag() {
		return Boolean.valueOf(mappedValues
				.get(BrowserConstant.highlightElementFlag.toString()));
	}

	public boolean isScreenShotFlag() {
		return Boolean.valueOf(mappedValues.get(BrowserConstant.screenShotFlag
				.toString()));
	}

	public boolean isMobileTest() {
		return Boolean.valueOf(mappedValues.get(BrowserConstant.mobileTest
				.toString()));
	}

}
