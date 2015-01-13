package com.springer.omelet.test.data.validateBrowsers;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.springer.omelet.data.DriverConfigurations;
import com.springer.omelet.data.driverconf.ValidateBrowserRules;
import com.springer.omelet.exception.FrameworkException;

public class LocalBrowserRuleTest {

	Map<String,String> browserMap;
	
	@BeforeMethod
	public void setup(){
		browserMap = new HashMap<String, String>();
		browserMap.put(DriverConfigurations.FrameworkConfig.remoteflag.toString(), "false");
	}
	
	@Test(expectedExceptions=FrameworkException.class)
	public void verifyIfBrowserIsChromeAndServerPathEmpty(){
		
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.browsername.toString(), "chrome");
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.chromeserverpath.toString(), "");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
	}
	
	@Test(expectedExceptions=FrameworkException.class)
	public void verifyIfBrowserIsIEAndServerPathEmpty(){
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.browsername.toString(), "IE");
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.ieserverpath.toString(), "");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
	}
	
	@Test()
	public void verifyIfBrowserIsChromeAndServerPathNotEmpty(){
		//There should not be any FrameworkExceptionThrown
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.browsername.toString(), "chrome");
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.chromeserverpath.toString(), "testServerPath");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
	}
	
	@Test()
	public void verifyIfBrowserIsIEAndServerPathNotEmpty(){
		//There should not be any FrameworkExceptionThrown
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.browsername.toString(), "IE");
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.ieserverpath.toString(), "testServerPath");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
	}
}
