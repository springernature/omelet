package com.springer.omelet.data.driverconf;

import java.util.HashMap;
import java.util.Map;

import com.springer.omelet.data.DriverConfigurations;

/**
 * Class to get Driver
 * @author kapil
 *
 */

public class PrepareDriverConf {
	IBrowserConf browserValue;
	private static final String FILENAME = "Framework.properties";
	private Map<String,String> refinedBrowserConf = new HashMap<String, String>();
	private Map<String,String> clientBrowserConf = new HashMap<String, String>();
	
	public PrepareDriverConf(Map<String,String> clientBrowserConf){
		this.clientBrowserConf = clientBrowserConf;
	}
	
	public PrepareDriverConf(){
		this.clientBrowserConf = new HashMap<String, String>();
	}
	
	public PrepareDriverConf refineBrowserValues(){
		RefinedBrowserConf rbc = new RefinedBrowserConf(clientBrowserConf, FILENAME);
		for(DriverConfigurations.LocalEnvironmentConfig localConfig:DriverConfigurations.LocalEnvironmentConfig.values()){
			updateRefinedMap(localConfig.toString(), rbc.get(localConfig.toString(), localConfig.get()));
		}
		
		for(DriverConfigurations.HubConfig hubConfig:DriverConfigurations.HubConfig.values()){
			updateRefinedMap(hubConfig.toString(), rbc.get(hubConfig.toString(), hubConfig.get()));
		}
		
		for(DriverConfigurations.FrameworkConfig frameworkConfig:DriverConfigurations.FrameworkConfig.values()){
			updateRefinedMap(frameworkConfig.toString(), rbc.get(frameworkConfig.toString(), frameworkConfig.get()));
		}
		
		for(DriverConfigurations.BrowserStackConfig bsConfig:DriverConfigurations.BrowserStackConfig.values()){
			updateRefinedMap(bsConfig.toString(), rbc.get(bsConfig.toString(), bsConfig.get()));
		}
		
		return this;
		
	}
	
	public PrepareDriverConf checkForRules(){
		ValidateBrowserRules vbr = new ValidateBrowserRules(refinedBrowserConf);
		vbr.validate();
		return this;
		
	}
	
	private void updateRefinedMap(String key,String refinedValue){
		refinedBrowserConf.put(key, refinedValue);
	}
	
	public IBrowserConf get(){
		return new BrowserConfR(refinedBrowserConf);	
	}

}
