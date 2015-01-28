package pageobjects;

import omelet.common.ExpectedConditionExtended;
import omelet.data.IProperty;
import omelet.driver.DriverUtility;
import omelet.exception.FrameworkException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GooglePage {

	private WebDriver driver;
	private IProperty prop;

	@FindBy(name = "q")
	private WebElement searchBar;

	// Simple Test Contructor
	public GooglePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Constructor using Property use
	public GooglePage(WebDriver driver, IProperty prop) {
		this.driver = driver;
		this.prop = prop;
		PageFactory.initElements(driver, this);
	}

	public GooglePage load(String url) {
		driver.get(url);
		return this;
	}

	public GooglePage loadFromProperty() {
		driver.get(prop.getValue("Google_url"));
		return this;
	}

	public GooglePage isLoaded() {
		if (null == DriverUtility.waitFor(
				ExpectedConditionExtended.elementToBeClickable(searchBar),
				driver, 15)) {
			throw new FrameworkException(
					"Not able to load Google Home page in 15 seconds");
		}
		return this;
	}

	public GooglePage search(String searchText) {
		searchBar.sendKeys(searchText + Keys.RETURN);
		return this;
	}

	public void clickOnLink(String linkText){
		driver.findElement(By.linkText(linkText)).click();
	}
}
