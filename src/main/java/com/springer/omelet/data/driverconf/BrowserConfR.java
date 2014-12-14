package com.springer.omelet.data.driverconf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.springer.omelet.data.DriverConfigurations;

public class BrowserConfR implements IBrowserConf {

	private Map<String, String> mappedValues;
	//private List<String> bsURLs = new ArrayList<String>();

	public BrowserConfR(Map<String, String> completeBrowserMap) {
		mappedValues = completeBrowserMap;
	}

	public String getBrowser() {
		return mappedValues
				.get(DriverConfigurations.LocalEnvironmentConfig.browserName
						.toString());
	}

	public String getBrowserVersion() {
		return mappedValues
				.get(DriverConfigurations.BrowserStackConfig.browserVersion
						.toString());
	}

	public String getOsName() {
		return mappedValues.get(DriverConfigurations.BrowserStackConfig.os
				.toString());
	}

	public String getOsVersion() {
		return mappedValues
				.get(DriverConfigurations.BrowserStackConfig.osVersion
						.toString());
	}

	public boolean isBrowserStackSwitch() {
		return Boolean.valueOf(mappedValues
				.get(DriverConfigurations.BrowserStackConfig.bsSwitch
						.toString()));
	}

	public String getBsUserName() {
		return mappedValues
				.get(DriverConfigurations.BrowserStackConfig.bs_userName
						.toString());
	}

	public String getBsPassword() {
		return mappedValues.get(DriverConfigurations.BrowserStackConfig.bs_key
				.toString());
	}

	public boolean isRemoteFlag() {
		return Boolean
				.valueOf(mappedValues
						.get(DriverConfigurations.FrameworkConfig.remoteFlag
								.toString()));
	}

	public String getRemoteURL() {
		return mappedValues.get(DriverConfigurations.HubConfig.remoteURL
				.toString());
	}

	public Integer getDriverTimeOut() {
		return Integer.valueOf(mappedValues
				.get(DriverConfigurations.FrameworkConfig.driverTimeOut
						.toString()));
	}

	public Integer getRetryFailedTestCaseCount() {
		return Integer.valueOf(mappedValues
				.get(DriverConfigurations.FrameworkConfig.retryFailedTestCase
						.toString()));
	}

	public boolean isBsLocalTesting() {
		return Boolean.valueOf(mappedValues
				.get(DriverConfigurations.BrowserStackConfig.bs_localTesting
						.toString()));
	}

	public List<String> getBsURLs() {
		List<String> bsURLs = new ArrayList<String>();
		if (bsURLs.isEmpty()) {
			String url = mappedValues
					.get(DriverConfigurations.BrowserStackConfig.bs_urls
							.toString());
			if (url.contains(";")) {
				String[] urls = url.split(";");
				for (int i = 0; i < urls.length; i++) {
					bsURLs.add(urls[i].trim());
				}
			} else {
				bsURLs.add(url);
			}
		}
		return bsURLs;
	}

	public String getDevice() {
		return mappedValues.get(DriverConfigurations.BrowserStackConfig.device
				.toString());
	}

	public String getPlatform() {
		return mappedValues
				.get(DriverConfigurations.BrowserStackConfig.platform
						.toString());
	}

	public String getLocalIEServerPath() {
		return mappedValues
				.get(DriverConfigurations.LocalEnvironmentConfig.ieServerPath
						.toString());
	}

	public String getLocalChromeServerPath() {
		return mappedValues
				.get(DriverConfigurations.LocalEnvironmentConfig.chromeServerPath
						.toString());
	}

	public boolean isHighLightElementFlag() {
		return Boolean.valueOf(mappedValues
				.get(DriverConfigurations.FrameworkConfig.highlightElementFlag
						.toString()));
	}

	public boolean isScreenShotFlag() {
		return Boolean.valueOf(mappedValues
				.get(DriverConfigurations.FrameworkConfig.screenShotFlag
						.toString()));
	}

	public boolean isMobileTest() {
		return Boolean.valueOf(mappedValues
				.get(DriverConfigurations.BrowserStackConfig.mobileTest
						.toString()));
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
				if (isBsLocalTesting()) {
					sb.append(",BrowserStack LocalTesting:"
							+ "<span style='font-weight:normal'>"
							+ isBsLocalTesting() + "</span>");
				}
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
		if (this == obj) {
			return true;
		}
		if (obj == null || (obj.getClass() != this.getClass())) {
			return false;
		}
		BrowserConfR secondObj = (BrowserConfR) obj;

		if (this.isRemoteFlag() == secondObj.isRemoteFlag()) {
			if (this.isRemoteFlag() == true) {
				/*
				 * return this.getBrowser() == secondObj.getBrowser(); else{
				 */
				if (this.isBrowserStackSwitch() == secondObj
						.isBrowserStackSwitch()) {
					if (this.isBrowserStackSwitch() == true) {
						if (this.isMobileTest() == false) {
							return (this.getBrowser().equals(
									secondObj.getBrowser())
									&& this.getBrowserVersion().equals(
											secondObj.getBrowserVersion())
									&& this.getOsName().equals(
											secondObj.getOsName()) && this
									.getOsVersion().equals(
											secondObj.getOsVersion()));
						} else {
							return (this.getDevice().equals(
									secondObj.getDevice())
									&& this.getBrowser().equals(
											secondObj.getBrowser()) && this
									.getPlatform().equals(
											secondObj.getPlatform()));
						}
					} else {
						return (this.getBrowser()
								.equals(secondObj.getBrowser()));
					}
				} else {
					return false;
				}
			} else {

				return this.getBrowser().equals(secondObj.getBrowser());
			}
		}
		return false;
	}
	
	public String getDataSource()
	{
		return mappedValues.get(DriverConfigurations.FrameworkConfig.dataSource.toString());
	}
}
