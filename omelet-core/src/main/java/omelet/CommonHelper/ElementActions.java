package omelet.CommonHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Element Action class has all the general actions needed for element interactions Use
 * {@link #click(WebElement)} Use {@link #EnterText(WebElement , String)}
 *
 * @author mlp8076
 */
import omelet.driver.Driver;

public class ElementActions {

	private static final Logger Log = LogManager.getLogger(ElementActions.class);
	/**
	 * @param element
	 *            pass the webelement to enter text
	 * @param value
	 *            pass the value to enter in text element
	 * @author mlp8076 method to enter text in text box element
	 */

	public static void EnterText(WebElement element, String value) {

		CommonHelper.expclicitWait(10).until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}

	/**
	 * Method to double click on element
	 *
	 * @param element
	 *            pass the web element for double click
	 * @return true when double click action happened
	 * @author mlp8076
	 */

	public static boolean doubleClick(WebElement element) {
		Actions action = new Actions(Driver.getDriver());
		CommonHelper.WaitForElement(element, 10);
		action.doubleClick(element).perform();
		return true;
	}

	/**
	 * Method to normal click on element
	 *
	 * @param element
	 *            pass the Web element for click
	 * @return true when click on element happened
	 * @author mlp8076
	 */

	public static boolean click(WebElement element) {

		if (!clickBySeleniumMethod(element)) {

			clickByJs(element);

			return true;
		}

		return false;
	}

	/**
	 * Method to call selenium click on element
	 *
	 * @param element
	 *            pass the Web element for click
	 * @return true when click on element happened
	 * @author mlp8076
	 */

	private static boolean clickBySeleniumMethod(WebElement element) {

		CommonHelper.WaitForElement(element, 30);
		CommonHelper.expclicitWait(10).until(ExpectedConditions.elementToBeClickable(element));

		try {
			Log.info("trying normal click on element " + element.getText());
			element.click();
			return true;
		} catch (ElementClickInterceptedException e) {
			return false;
		}

	}

	/**
	 * Method to click by js on element
	 *
	 * @param element
	 *            pass the Web element for click
	 * @return true when click on element happened
	 * @author mlp8076
	 */

	private static boolean clickByJs(WebElement element) {

		try {
			Log.info("Normal click failed now trying with js click on element " + element.getText());

			JavascriptExecutor executor = (JavascriptExecutor) (Driver.getDriver());

			executor.executeScript("arguments[0].click();", element);

			return true;

		} catch (ElementClickInterceptedException excpetion) {

			return false;
		}
	}

	/**
	 * Method to mouse hover on element
	 *
	 * @param element
	 *            pass the Web element for click
	 * @return true when click on element happened
	 * @author mlp8076
	 */
	public static boolean mouseHover(WebElement element) {
		CommonHelper.WaitForElement(element, 20);
		Actions action = new Actions(Driver.getDriver());
		action.moveToElement(element).build().perform();
		return true;

	}
}
