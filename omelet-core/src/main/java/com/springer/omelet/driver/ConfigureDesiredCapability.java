package com.springer.omelet.driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

public class ConfigureDesiredCapability {
	
	DesiredCapabilities dc;
	private static Map<String,DesiredCapabilities> lDesiredCapMap = new HashMap<String, DesiredCapabilities>();
	
	
	public static void load(Map<String,DesiredCapabilities> desiredCapMap){
		lDesiredCapMap = desiredCapMap;
	}
	
	protected static DesiredCapabilities getDesiredCapabilities(String browserName){
		if(lDesiredCapMap.containsKey(browserName)){
			return lDesiredCapMap.get(browserName);
		}
		return null;
	}

}
