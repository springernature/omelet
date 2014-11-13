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
		browserMap.put(DriverConfigurations.FrameworkConfig.remoteFlag.toString(), "false");
	}
	
	@Test(expectedExceptions=FrameworkException.class)
	public void verifyIfBrowserIsChromeAndServerPathEmpty(){
		
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.browserName.toString(), "chrome");
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.chromeServerPath.toString(), "");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}
	
	@Test(expectedExceptions=FrameworkException.class)
	public void verifyIfBrowserIsIEAndServerPathEmpty(){
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.browserName.toString(), "IE");
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.ieServerPath.toString(), "");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}
	
	@Test()
	public void verifyIfBrowserIsChromeAndServerPathNotEmpty(){
		//There should not be any FrameworkExceptionThrown
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.browserName.toString(), "chrome");
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.chromeServerPath.toString(), "testServerPath");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}
	
	@Test()
	public void verifyIfBrowserIsIEAndServerPathNotEmpty(){
		//There should not be any FrameworkExceptionThrown
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.browserName.toString(), "IE");
		browserMap.put(DriverConfigurations.LocalEnvironmentConfig.ieServerPath.toString(), "testServerPath");
		ValidateBrowserRules vbr = new ValidateBrowserRules(browserMap);
		vbr.checkAndThrowExceptionForLocalBrowser();
		vbr.checkAndThrowExceptionForBrowserStack();
		vbr.checkAndThrowExceptionForRemote();
	}
}
