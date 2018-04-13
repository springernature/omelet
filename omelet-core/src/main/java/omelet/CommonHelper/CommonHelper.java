package omelet.CommonHelper;

import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

		// expclicitWait(20).until(ExpectedConditions.numberOfWindowsToBe(2));
		// timeOut(10);
		Set<String> windowHandles = Driver.getDriver().getWindowHandles();

		Iterator<String> itr = windowHandles.iterator();

		Log.info("Ids of all windows are " + windowHandles);

		if (windowTitle == null || windowTitle.isEmpty()) {
			while (itr.hasNext()) {

				Driver.getDriver().switchTo().window(itr.next());

				Log.info("Title of window is " + Driver.getDriver().getTitle());
			}

		} else {

			while (itr.hasNext()) {

				Driver.getDriver().switchTo().window(itr.next());

				Log.info("Title of window is " + Driver.getDriver().getTitle());

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

	public static void switchToFrame(String frameName) {
		expclicitWait(10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
	}

	/**
	 * Method to switch to frame
	 *
	 * @param frameElement
	 *            pass the frame webelement to switch to frame
	 * @author mlp8076
	 */

	public static void switchToFrame(WebElement frameElement) {
		expclicitWait(10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	/**
	 * Method to statically wait uses Thread.sleep method
	 *
	 * @param timeOutInSeconds
	 *            pass the seconds to wait
	 * @author mlp8076
	 */

	public static void waitInSeconds(int timeOutInSeconds) {
		try {
			Thread.sleep(timeOutInSeconds * 1000);
		} catch (Exception e) {
		}

	}

	/**
	 * Method to check weather element is displayed or not
	 *
	 * @param element
	 *            pass the element to check if it is diplayed or not
	 * @author mlp8076
	 */
	public static boolean isDisplayed(WebElement element) {

		expclicitWait(10).until(ExpectedConditions.visibilityOf(element));
		return element.isDisplayed();
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
}
