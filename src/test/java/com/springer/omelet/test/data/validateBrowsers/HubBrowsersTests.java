package com.springer.omelet.test.data.validateBrowsers;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.springer.omelet.data.DriverConfigurations;
import com.springer.omelet.data.driverconf.ValidateBrowserRules;
import com.springer.omelet.exception.FrameworkException;

public class HubBrowsersTests {
	
	Map<String,String> browserMap;
	@BeforeMethod
	public void setup(){
		browserMap = new HashMap<String, String>();
		browserMap.put(DriverConfigurations.FrameworkConfig.remoteFlag.toString(), "True");
	}
	@Test(expectedExceptions=FrameworkException.class)
	public void ifRemoteURLEmpty(){
		browserMap.put(DriverConfigurations.HubConfig.host.toString(), "");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
		
	}
	
	@Test
	public void ifRemoteUrlPresent(){
		//there should be no framework exception
		browserMap.put(DriverConfigurations.HubConfig.host.toString(), "testURL");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}

}
