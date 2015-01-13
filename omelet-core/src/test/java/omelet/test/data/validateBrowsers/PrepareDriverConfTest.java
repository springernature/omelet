package omelet.test.data.validateBrowsers;

import java.util.HashMap;
import java.util.Map;

import omelet.data.DriverConfigurations;
import omelet.data.driverconf.IBrowserConf;
import omelet.data.driverconf.PrepareDriverConf;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrepareDriverConfTest {
	
	Map<String,String> browserConf = new HashMap<String, String>();
	
	@Test
	public void getIBrowserConfiguration(){
		PrepareDriverConf pdC = new PrepareDriverConf(browserConf);
		IBrowserConf browser = pdC.refineBrowserValues().checkForRules().get();
		Assert.assertEquals(browser.getBrowser().toLowerCase(), DriverConfigurations.LocalEnvironmentConfig.browsername.get().toLowerCase());
	}

}
