package com.springer.omelet.test.data;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.springer.omelet.data.DriverConfigurations;

public class BrowserConfigurationTest {
	static final String REMOEFLAG = "false";
	static final String DRIVERTIMEOUT = "30";
	static final String RETRYFAILEDTESTCASE = "0";
	static final String HIGHLIGHTELEMENTFLAG = "false";
	static final String SCREENSHOTFLAG = "true";

	static final String BSSWITCH = "false";
	static final String BSLOCALTESTING = "false";
	static final String MOBILETEST = "false";
	static final String BSUSERNAME = "";
	static final String BSKEY = "";
	static final String BSURLS = "";
	static final String DEVICE = "";
	static final String BROWSERNAME = "FireFox";
	static final String BROWSERVERSION = "";
	static final String OS = "";
	static final String OSVERSION = "";
	static final String PLATFORM = "";

	static final String BROWSERNAMELOCAL = "FireFox";
	static final String IESERVERPATH = "";
	static final String CHROMESERVERPATH = "";

	static final String REMOTEURL = "";

	@Test
	public void verifyFrameworkConfigDefaults() {
		Assert.assertEquals(DriverConfigurations.FrameworkConfig.remoteFlag.get(), REMOEFLAG);
		Assert.assertEquals(DriverConfigurations.FrameworkConfig.driverTimeOut.get(),
				DRIVERTIMEOUT);
		Assert.assertEquals(DriverConfigurations.FrameworkConfig.retryFailedTestCase.get(),
				RETRYFAILEDTESTCASE);
		Assert.assertEquals(DriverConfigurations.FrameworkConfig.highlightElementFlag.get(),
				HIGHLIGHTELEMENTFLAG);
		Assert.assertEquals(DriverConfigurations.FrameworkConfig.screenShotFlag.get(),
				SCREENSHOTFLAG);
	}
	
	@Test
	public void verifyBrowserStackConfigDefaults() {
	
		Assert.assertEquals(
				DriverConfigurations.CloudConfig.userName.get(),
				BSUSERNAME);
		Assert.assertEquals(
				DriverConfigurations.CloudConfig.key.get(), BSKEY);
		Assert.assertEquals(
				DriverConfigurations.CloudConfig.bs_urls.get(), BSURLS);
		Assert.assertEquals(
				DriverConfigurations.LocalEnvironmentConfig.browserName.get(),
				BROWSERNAME);
	}

	@Test
	public void verifyLocalConfigDefaults() {
		Assert.assertEquals(DriverConfigurations.LocalEnvironmentConfig.browserName.get(),
				BROWSERNAMELOCAL);
		Assert.assertEquals(DriverConfigurations.LocalEnvironmentConfig.ieServerPath.get(),
				IESERVERPATH);
		Assert.assertEquals(DriverConfigurations.LocalEnvironmentConfig.chromeServerPath.get(),
				CHROMESERVERPATH);
	}
	
	@Test
	public void verifyHubConfigDefaults() {
		Assert.assertEquals(DriverConfigurations.HubConfig.host.get(),
				REMOTEURL);
	}
}
