#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package pageobjects;

import java.util.List;

import omelet.common.ExpectedConditionExtended;
import omelet.data.IProperty;
import omelet.driver.DriverUtility;
import omelet.exception.FrameworkException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class GooglePage {
	
	private WebDriver driver;
	private IProperty prop;
	
	@FindBy(name = "q")
	private WebElement searchBar;
	@FindBys(@FindBy(css = ".rc .r a"))
	private List<WebElement> searchReturnLinks;
	
	//Simple Test Contructor
	public GooglePage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	//Constructor using Property use
	public GooglePage(WebDriver driver,IProperty prop){
		this.driver = driver;
		this.prop = prop;
		PageFactory.initElements(driver, this);
	}
	
	public GooglePage load(String url){
		driver.get(url);
		return this;
	}
	
	public GooglePage loadFromProperty(){
		driver.get(prop.getValue("Google_url"));
		return this;
	}
	
	public GooglePage isLoaded(){
		if(null == DriverUtility.waitFor(ExpectedConditionExtended.elementToBeClickable(searchBar), driver, 5)){
			throw new FrameworkException("Not able to load Google Home page in 5 seconds");
		}
		return this;
	}
	
	public GooglePage search(String searchText){
		searchBar.sendKeys(searchText+Keys.RETURN);
		return this;
	}
	
	public void clickOnLink(int indexOfTheLink){
		searchReturnLinks.get(indexOfTheLink).click();
	}


}
