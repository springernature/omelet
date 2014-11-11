package com.springer.omelet.test.data;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.springer.omelet.data.BrowserConstant;

public class BrowserConstantTest {

	static final String DEVICE = "";
	static final String BROWSERNAME = "FireFox";
	static final String BROWSERVERSION = "";
	static final String OS = "";
	static final String OSVERSION = "";
	static final String BSSWITCH = "false";
	static final String BSUSERNAME = "";
	static final String BSKEY = "";
	static final String REMOEFLAG = "false";
	static final String REMOTEURL = "";
	static final String DRIVERTIMEOUT = "30";
	static final String BSLOCALTESTING = "false";
	static final String BSURLS = "";
	static final String PLATFORM = "";
	static final String RETRYFAILEDTESTCASES = "0";
	static final String IESERVERPATH = "";
	static final String CHROMESERVERPATH = "";
	static final String HIGHLIGHTELEMENTFLAG = "false";
	static final String SCREENSHOTFLAG = "true";
	static final String MOBILETEST = "false";

	@Test
	public void verifyEnumDefaultValues() {
		Assert.assertEquals(BrowserConstant.device.get(), DEVICE);
		Assert.assertEquals(BrowserConstant.browserName.get(), BROWSERNAME);
		Assert.assertEquals(BrowserConstant.browserVersion.get(),
				BROWSERVERSION);
		Assert.assertEquals(BrowserConstant.os.get(), OS);
		Assert.assertEquals(BrowserConstant.osVersion.get(), OSVERSION);
		Assert.assertEquals(BrowserConstant.bsSwitch.get(), BSSWITCH);
		Assert.assertEquals(BrowserConstant.bs_userName.get(), BSUSERNAME);
		Assert.assertEquals(BrowserConstant.bs_key.get(), BSKEY);
		Assert.assertEquals(BrowserConstant.remoteFlag.get(), REMOEFLAG);
		Assert.assertEquals(BrowserConstant.remoteURL.get(), REMOTEURL);
		Assert.assertEquals(BrowserConstant.driverTimeOut.get(), DRIVERTIMEOUT);
		Assert.assertEquals(BrowserConstant.bs_localTesting.get(),
				BSLOCALTESTING);
		Assert.assertEquals(BrowserConstant.bs_urls.get(), BSURLS);
		Assert.assertEquals(BrowserConstant.platform.get(), PLATFORM);
		Assert.assertEquals(BrowserConstant.retryFailedTestCase.get(),
				RETRYFAILEDTESTCASES);
		Assert.assertEquals(BrowserConstant.ieServerPath.get(), IESERVERPATH);
		Assert.assertEquals(BrowserConstant.chromeServerPath.get(),
				CHROMESERVERPATH);
		Assert.assertEquals(BrowserConstant.highlightElementFlag.get(),
				HIGHLIGHTELEMENTFLAG);
		Assert.assertEquals(BrowserConstant.screenShotFlag.get(),
				SCREENSHOTFLAG);
		Assert.assertEquals(BrowserConstant.mobileTest.get(), MOBILETEST);
	}
}
