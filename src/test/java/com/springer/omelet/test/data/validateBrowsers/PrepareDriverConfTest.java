package com.springer.omelet.test.data.validateBrowsers;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.springer.omelet.data.DriverConfigurations;
import com.springer.omelet.data.driverconf.IBrowserConf;
import com.springer.omelet.data.driverconf.PrepareDriverConf;

public class PrepareDriverConfTest {
	
	Map<String,String> browserConf = new HashMap<String, String>();
	
	@Test
	public void getIBrowserConfiguration(){
		PrepareDriverConf pdC = new PrepareDriverConf(browserConf);
		IBrowserConf browser = pdC.refineBrowserValues().checkForRules().get();
		Assert.assertEquals(browser.getBrowser().toLowerCase(), DriverConfigurations.LocalEnvironmentConfig.browserName.get().toLowerCase());
	}

}
