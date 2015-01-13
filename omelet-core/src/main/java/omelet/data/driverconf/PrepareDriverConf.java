package omelet.data.driverconf;

import java.util.HashMap;
import java.util.Map;

import omelet.data.DriverConfigurations;

/**
 * Class to get Driver
 * @author kapil
 *
 */

public class PrepareDriverConf {
	IBrowserConf browserValue;
	private static final String FILENAME = "Framework.properties";
	private Map<String,String> refinedBrowserConf = new HashMap<String, String>();
	RefinedBrowserConf rbc;
	
	public PrepareDriverConf(Map<String,String> clientBrowserConf){
		rbc = new RefinedBrowserConf(clientBrowserConf, FILENAME);
		
	}
	
	public PrepareDriverConf(){
		this(new HashMap<String, String>());
	}
	
	public PrepareDriverConf refineBrowserValues(){
		for(DriverConfigurations.LocalEnvironmentConfig localConfig:DriverConfigurations.LocalEnvironmentConfig.values()){
			updateRefinedMap(localConfig.toString(), rbc.get(localConfig.toString(), localConfig.get()));
		}
		
		for(DriverConfigurations.HubConfig hubConfig:DriverConfigurations.HubConfig.values()){
			updateRefinedMap(hubConfig.toString(), rbc.get(hubConfig.toString(), hubConfig.get()));
		}
		
		for(DriverConfigurations.FrameworkConfig frameworkConfig:DriverConfigurations.FrameworkConfig.values()){
			updateRefinedMap(frameworkConfig.toString(), rbc.get(frameworkConfig.toString(), frameworkConfig.get()));
		}
		
		for(DriverConfigurations.CloudConfig bsConfig:DriverConfigurations.CloudConfig.values()){
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
		return new BrowserConfR(refinedBrowserConf,rbc.getDesiredCapabilities());	
	}

}
