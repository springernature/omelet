package omelet.CommonHelper;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import omelet.driver.Driver;

public class CommonHelper {

	private static final Logger Log = LogManager.getLogger(CommonHelper.class);

	/**
	 * Web driver wait will wait for defined period of time
	 *
	 * @param waitTime
	 *            pass the amount of time needed to wait for element in seconds
	 * @param element
	 *            pass the element to wait
	 * @author mlp8076
	 */

	public static void WaitForElement(WebElement element, int waitTime) {

		expclicitWait(waitTime).until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Method to create webdriver wait object
	 *
	 * @param waitTimeInSeconds
	 *            pass number of seconds to wait for webdriver wait object
	 */

	public static WebDriverWait expclicitWait(int waitTimeInSeconds) {

		return new WebDriverWait(Driver.getDriver(), waitTimeInSeconds);
	}

	/**
	 * Method to switch to latest window opened
	 *
	 * @param windowTitle
	 *            Pass the window title to switch to particular window, else pass "
	 *            " or null to switch to latest window
	 * @author mlp8076
	 */

	public static void switchToWindow(String windowTitle) {

		Set<String> windowHandles = Driver.getDriver().getWindowHandles();

		Iterator<String> itr = windowHandles.iterator();

		Log.debug("Ids of all windows are " + windowHandles);

		if (windowTitle == null || windowTitle.isEmpty()) {
			while (itr.hasNext()) {

				Driver.getDriver().switchTo().window(itr.next());

				Log.debug("Title of window is " + Driver.getDriver().getTitle());
			}

		} else {

			while (itr.hasNext()) {

				Driver.getDriver().switchTo().window(itr.next());

				Log.debug("Title of window is " + Driver.getDriver().getTitle());

				if (Driver.getDriver().getTitle().contains(windowTitle)) {
					break;
				}
			}
		}
	}

	/**
	 * Method to switch to frame
	 *
	 * @param frameName
	 *            pass the frame name or id to switch to frame
	 * @author mlp8076
	 */

	public static boolean switchToFrame(String frameName) {
		Log.debug("trying to switch to frame using framename" + frameName);

		boolean isSwitchedToFrame = false;

		try {

			if (frameName != null && !frameName.isEmpty()) {

				expclicitWait(15).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
				isSwitchedToFrame = true;

			}
		} catch (Exception e) {
			// throw new ElementException("Switch To Frame Action Failed");
		}

		Log.debug("switched to frame and is:" + isSwitchedToFrame);
		return isSwitchedToFrame;

	}

	/**
	 * Method to switch to frame
	 *
	 * @param frameElement
	 *            pass the frame webelement to switch to frame
	 * @author mlp8076
	 */

	public static boolean switchToFrame(WebElement frameElement) {
		Log.debug("trying to switch to frame using webelement");

		boolean isSwitchedToFrame = false;

		try {

			if (frameElement != null) {

				expclicitWait(15).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
				isSwitchedToFrame = true;

			}
		} catch (Exception e) {
			// throw new ElementException("Switch To Frame Action Failed");
		}

		Log.debug("switched to frame and is:" + isSwitchedToFrame);
		return isSwitchedToFrame;

	}

	/**
	 * Method to switch to frame
	 *
	 * @param frameElement
	 *            pass the frame number to switch to frame
	 * @author mlp8076
	 */

	public static boolean switchToFrame(Integer frameNubmer) {
		Log.debug("trying to switch to frame using frame number" + frameNubmer);

		boolean isSwitchedToFrame = false;

		try {

			if (frameNubmer != null && !(frameNubmer < 0)) {

				expclicitWait(15).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNubmer));
				isSwitchedToFrame = true;

			}
		} catch (Exception e) {
			// throw new ElementException("Switch To Frame Action Failed");
		}

		Log.debug("switched to frame and is:" + isSwitchedToFrame);
		return isSwitchedToFrame;

	}

	/**
	 * Method to statically wait uses Thread.sleep method
	 *
	 * @param timeOutInSeconds
	 *            pass the seconds to wait
	 * @author mlp8076
	 */

	public static void waitInSeconds(int timeOutInSeconds) {
		Log.debug(" waitInSeconds(): " + timeOutInSeconds + " seconds");

		try {
			Thread.sleep(timeOutInSeconds * 1000);
		} catch (Exception e) {
			Log.debug(e.getMessage());
		}
	}

	/**
	 * Method to check weather element is displayed or not
	 *
	 * @param element
	 *            pass the element to check if it is diplayed or not
	 * @author mlp8076
	 */
	public static boolean isElementDisplayed(WebElement element) {

		expclicitWait(20).until(ExpectedConditions.visibilityOf(element));

		return element.isDisplayed();
	}

	/**
	 * Method to check weather element is enabled or not
	 *
	 * @param element
	 *            pass the element to check if it is enabled or not
	 * @author mlp8076
	 */
	protected boolean isElementEnabled(WebElement element) {

		if (isElementDisplayed(element))

			return element.isEnabled();

		return false;
	}

	/**
	 * open the passed URL
	 * 
	 * @author mlp8076
	 * @param url
	 *            pass the URL to open the application
	 */

	public static void getURL(String url) {

		Driver.getDriver().get(url);
	}

	/**
	 * Method to return the title of page
	 * 
	 * @return title of page
	 */
	public static String getPageTitle() {
		return Driver.getDriver().getTitle();
	}

	/**
	 * This method checks for the alert to be present.
	 * 
	 * @param driver
	 * @return
	 */
	public static boolean isAlertPresent() {

		Log.debug("Check if Alert is present or not");

		try {
			Driver.getDriver().switchTo().alert();
			Log.debug("***Alert Exists***");
			return true;
		} catch (NoAlertPresentException e) {
			Log.debug("***Alert Not Found***" + e.getMessage());
			return false;
		}
	}

	/**
	 * This method accepts the alert.
	 * 
	 * @param driver
	 */
	public static void dismissAlert() {
		try {
			expclicitWait(5).until(ExpectedConditions.alertIsPresent());
			Alert alert = Driver.getDriver().switchTo().alert();
			String text = alert.getText();
			alert.dismiss();
			Log.debug("Dismmissed alert");
			Log.debug("Alert Message : " + text);
		} catch (TimeoutException e) {
			Log.warn("Alert not present");
		}
	}

	/**
	 * This method accepts the alert.
	 * 
	 * @param driver
	 */
	public static boolean acceptAlert() {
		try {
			expclicitWait(5).until(ExpectedConditions.alertIsPresent());
			Alert alert = Driver.getDriver().switchTo().alert();
			String text = alert.getText();
			alert.accept();
			Log.debug("Alert accepted");
			Log.debug("Alert Text : " + text);
			return true;
		} catch (TimeoutException e) {
			Log.warn("Alert not present");
			return false;
		}
	}

	/**
	 * accept alert with js
	 */

	public static void acceptAlertbyJS() {

		Log.debug("Trying to accept alert by js");

		((JavascriptExecutor) Driver.getDriver()).executeScript("window.confirm = function(msg) { return true; }");

		Log.debug("Alert is accepted by js");
	}

	/**
	 * To get next Random number
	 * 
	 * @return random integer
	 */
	public static int getRandomNumber() {
		return new Random().nextInt();
	}

	/**
	 * This Method is used to switch back to default content after switching to any
	 * frame.
	 * 
	 */
	public static void switchToDefaultContent() {

		Log.debug("switch to default content");

		Driver.getDriver().switchTo().defaultContent();

		Log.debug("switch to default content is successful");
	}

	/**
	 * this method will refresh the browser
	 * 
	 */
	public static void refreshBrowser() {

		Log.debug("before browser refresh");

		Driver.getDriver().navigate().refresh();

		Log.debug("after browser refresh");
	}
}
