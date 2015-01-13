package tests;

import omelet.data.IProperty;
import omelet.data.driverconf.IBrowserConf;
import omelet.driver.Driver;
import omelet.testng.support.SAssert;

import org.testng.annotations.Test;

import pageobjects.GooglePage;
import pageobjects.SeleniumPage;

public class SimpleGoogleTest {
	
	SAssert sassert = new SAssert();

	//@Test(description = "verify if Selenium title is as expected", enabled = true)
	public void verifySeleniumTitle_XML(IBrowserConf browserConf, IProperty prop) {
		System.out.println(browserConf.getCapabilities().getPlatform());
		GooglePage gp = new GooglePage(Driver.getDriver(browserConf), prop);
		gp.loadFromProperty().isLoaded().search("Selenium").clickOnLink(0);
		// Selenium

		SeleniumPage sp = new SeleniumPage(Driver.getDriver());
		sassert.assertEquals(sp.isLoaded().getTitle(),
				prop.getValue("Selenium_Title"),
				"Check for the title of the page");
		sassert.assertAll();
	}
	

	/*
	 * @Test(description = "verify if Selenium title is as expected",
	 * dataProviderClass = com.springer.omelet.data.DataProvider.class,
	 * dataProvider = "GoogleData",enabled=true)
	 */
	@Test
	public void verifySeleniumTitle_GoogleSheet(IBrowserConf browserConf,
			IProperty prop) {
		GooglePage gp = new GooglePage(Driver.getDriver(browserConf), prop);
		gp.loadFromProperty().isLoaded().search("Selenium").clickOnLink(0);
		// Selenium
		SeleniumPage sp = new SeleniumPage(Driver.getDriver());

		sassert.assertEquals(sp.isLoaded().getTitle(),
				prop.getValue("Selenium_Title"),
				"Check for the title of the page");
		sassert.assertAll();
	}


}
