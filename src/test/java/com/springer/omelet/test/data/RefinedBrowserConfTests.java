package com.springer.omelet.test.data;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.springer.omelet.common.Utils;
import com.springer.omelet.data.IProperty;
import com.springer.omelet.data.PropertyMapping;
import com.springer.omelet.data.driverconf.RefinedBrowserConf;

public class RefinedBrowserConfTests {
	String key = "bsUserName";
	String key1 = "bsKey";
	
	@BeforeMethod
	public void setup(){
		System.setProperty(key, "");
	}
	
	@Test
	public void ifCommandLineAndOtherPresent(){
		String commandLineValue  = "BrowserStackUserName";
		Map<String,String> clientMap = new HashMap<String, String>();
		System.setProperty(key, commandLineValue);
		clientMap.put(key, "bsUserTest");
		RefinedBrowserConf rbf = new RefinedBrowserConf(clientMap, "test.properties");
		Assert.assertEquals(rbf.get("bsUserName", "defaultUserName"), commandLineValue);
	}
	
	@Test
	public void verifyIfClientEnvPresentAndCommandLineBlank(){
		String commandLineValue  = "";
		String clientValue = "clientBsUser";
		Map<String,String> clientMap = new HashMap<String, String>();
		System.setProperty(key, commandLineValue);
		clientMap.put(key,clientValue );
		RefinedBrowserConf rbf = new RefinedBrowserConf(clientMap, "test.properties");
		Assert.assertEquals(rbf.get(key, "defaultUserName"), clientValue);
	}
	
	@Test
	public void verifyIfClient_CommandLine_Blank(){
		
		String commandLineValue  = "";
		String clientValue = "";
		String defaultValue="defaultUserName";
		Map<String,String> clientMap = new HashMap<String, String>();
		System.setProperty(key, commandLineValue);
		clientMap.put(key,clientValue );
		RefinedBrowserConf rbf = new RefinedBrowserConf(clientMap, "test.properties");
		Assert.assertEquals(rbf.get(key, defaultValue), defaultValue);	
	}
	@Test
	public void verifyCommandLineNotPresent(){
		String clientValue = "clientBsUser";
		Map<String,String> clientMap = new HashMap<String, String>();
		clientMap.put(key,clientValue);
		RefinedBrowserConf rbf = new RefinedBrowserConf(clientMap, "test.properties");
		Assert.assertEquals(rbf.get(key, "defaultUserName"), clientValue);
	}
	
	@Test
	public void verifyFrameworkPropertiesValueSet(){
		String dataFileName = "FrameworkPropTest.properties";
		Map<String,String> clientMap = new HashMap<String, String>();
		IProperty frameworkProp = new PropertyMapping(Utils.getResources(this, dataFileName));
		RefinedBrowserConf rbf = new RefinedBrowserConf(clientMap, dataFileName);
		Assert.assertEquals(rbf.get(key, "defaultUserName"), frameworkProp.getValue(key));
	}
	
	@Test
	public void frameworkBlanAndCheckDefault(){
		String dataFileName = "FrameworkPropTest.properties";
		String defaultValue = "defaultKey";
		Map<String,String> clientMap = new HashMap<String, String>();
		RefinedBrowserConf rbf = new RefinedBrowserConf(clientMap, dataFileName);
		Assert.assertEquals(rbf.get(key1, defaultValue), defaultValue);
	}

}
