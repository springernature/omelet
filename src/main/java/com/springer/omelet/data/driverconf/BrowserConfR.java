package com.springer.omelet.data.driverconf;

import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.springer.omelet.data.DriverConfigurations;

public class BrowserConfR implements IBrowserConf {

	private Map<String, String> mappedValues;
	private DesiredCapabilities dc;

	public BrowserConfR(Map<String, String> completeBrowserMap) {
		this(completeBrowserMap,new DesiredCapabilities());
	}
	
	public BrowserConfR(Map<String, String> completeBrowserMap,DesiredCapabilities dc){
		mappedValues = completeBrowserMap;
		this.dc = dc;
	}

	public String getBrowser() {
		return mappedValues
				.get(DriverConfigurations.LocalEnvironmentConfig.browserName
						.toString());
	}


	public String getuserName() {
		return mappedValues
				.get(DriverConfigurations.CloudConfig.userName
						.toString());
	}

	public String getKey() {
		return mappedValues.get(DriverConfigurations.CloudConfig.key
				.toString());
	}

	public boolean isRemoteFlag() {
		return Boolean
				.valueOf(mappedValues
						.get(DriverConfigurations.FrameworkConfig.remoteFlag
								.toString()));
	}

	public String getRemoteURL() {
		return mappedValues.get(DriverConfigurations.HubConfig.host
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


	/*public List<String> getBsURLs() {
		List<String> bsURLs = new ArrayList<String>();
		if (bsURLs.isEmpty()) {
			String url = mappedValues
					.get(DriverConfigurations.CloudConfig.bs_urls
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
*/

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

	

	/***
	 * Implementing toString as Html reports will look good
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Browser:" + "<span style='font-weight:normal'>"
				+ getBrowser() + "</span>");
		sb.append(", Capabilities:"
				+ "<span style='font-weight:normal'>"
				+ this.getCapabilities().toString() + "</span>");
	/*	if (isRemoteFlag()) {
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
		}*/
		return sb.toString();
	}

	@Override
	public int hashCode() {
		int hash = 7;

		if (this.isRemoteFlag() == true) {
			
				hash = 31 * hash + this.getBrowser().hashCode();
				hash = 31 * hash + this.getCapabilities().hashCode();
		} else {
			hash = 31 * hash + this.getBrowser().hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		//Check if remoteFlag=false then comparison of BrowserName
		//else if true then if DesiredCapability are same and BrowserName is same if 
		if(this == obj){
			return true;
		}
		if(obj == null || obj.getClass() != this.getClass()){
			return false;
		}
		BrowserConfR secondObj = (BrowserConfR)obj;
		if(this.isRemoteFlag() == secondObj.isRemoteFlag()){
			if(this.isRemoteFlag() == true){
				if(this.getBrowser().equals(secondObj.getBrowser())){
					if(this.getCapabilities().equals(secondObj.getCapabilities())){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				if(this.getBrowser().equals(secondObj.getBrowser())){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	public String getDataSource()
	{
		return mappedValues.get(DriverConfigurations.FrameworkConfig.dataSource.toString());
	}

	@Override
	public DesiredCapabilities getCapabilities() {
		return dc;
	}
	

	@Override
	public String host() {
		return mappedValues.get(DriverConfigurations.HubConfig.host.toString());
	}

	@Override
	public String port() {
		return mappedValues.get(DriverConfigurations.HubConfig.port.toString());
	}

	@Override
	public void updateCapabilities(DesiredCapabilities dc) {
		this.dc.merge(dc);
	}

}
