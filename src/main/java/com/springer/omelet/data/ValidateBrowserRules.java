package com.springer.omelet.data;

import java.util.HashMap;
import java.util.Map;

public class ValidateBrowserRules {

	Map<String, String> refinedBrowserConf = new HashMap<String, String>();
	StringBuilder exceptionMessage = new StringBuilder();
	IBrowserConf browserConf;

	public ValidateBrowserRules(Map<String, String> refinedBrowserConf) {
		this.refinedBrowserConf = refinedBrowserConf;
		browserConf = new BrowserConfiguration(refinedBrowserConf);
	}

	public void checkAndThrowExceptionForLocalBrowser() {
		// if remoteFlag is false and IE serverPath is empty and browserName =
		// IE then exception
		// if remoteFlag false and chromeServerPath is empty and browserName =
		// chrome then exception
		if (!browserConf.isRemoteFlag()) {
			if (browserConf.getBrowser().toLowerCase().startsWith("i")
					&& !"".equals(browserConf.getLocalIEServerPath())) {
				exceptionMessage
						.append("Not able to find IE Serverpath , Please set it!!");
			}

			if (browserConf.getBrowser().toLowerCase().startsWith("c")
					&& !"".equals(browserConf.getLocalChromeServerPath())) {
				exceptionMessage
						.append("Browser selected in Chrome and not able to find Chrome Server");
			}
		}

	}

	public void checkAndThrowExceptionForRemote() {
		// if remoteFlag=true and remotURL = "" then Exception and bsSwitch =
		// false
		if (browserConf.isRemoteFlag() && !browserConf.isBrowserStackSwitch()) {
			if ("".equals(browserConf.getRemoteURL())) {
				exceptionMessage
						.append("As the remote Flag is true , please set remoteURL");
			}
		}
	}

	public void checkAndThrowExceptionForBrowserStack() {
		if (browserConf.isRemoteFlag() && browserConf.isBrowserStackSwitch()) {
			if (!browserConf.isMobileTest()) {
				checkForBrowserStackBrowser();
			} else {
				checkForBrowserStackMobile();
			}

			if (browserConf.isBsLocalTesting()) {
				checkForBrowserStackLocalTesting();
			}
		}
		// if remoteFlag = true and bsSwitch = true
		// bskey and bsUserName and browserVersion , oS , os version
		// if localTesting then bsURLS
		// if mobileTest the device platform and browser FireFox then exception
	}

	private void checkForBrowserStackBrowser() {
		if ("".equals(browserConf.getBrowserVersion())) {
			exceptionMessage
					.append("As BS switch is on ,Please enter browserVersion");
		}
		if ("".equals(browserConf.getOsName())) {
			exceptionMessage.append("As BS switch is on ,Please enter OsName");
		}
		if ("".equals(browserConf.getOsVersion())) {
			exceptionMessage
					.append("As BS switch is on ,Please enter osversion");
		}
	}

	private void checkForBrowserStackLocalTesting() {
		if(browserConf.getBsURLs().isEmpty()){
			exceptionMessage.append("Please provide your local testing url for tunnel setup");
		}

	}

	private void checkForBrowserStackMobile() {
		if(browserConf.getDevice().isEmpty()){
			exceptionMessage.append("As we want to run on Mobile , please give the device");
		}
		if(browserConf.getPlatform().isEmpty()){
			exceptionMessage.append("Please set platform");
		}
		if(!browserConf.getBrowser().equalsIgnoreCase("android") || !browserConf.getBrowser().equalsIgnoreCase("iphone")||!browserConf.getBrowser().equalsIgnoreCase("ipad")){
			exceptionMessage.append("please set proper browserName for Mobile");
		}
	}


}
