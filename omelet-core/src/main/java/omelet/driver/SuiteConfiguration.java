/*******************************************************************************
 *
 * 	Copyright 2014 Springer Science+Business Media Deutschland GmbH
 * 	
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 * 	you may not use this file except in compliance with the License.
 * 	You may obtain a copy of the License at
 * 	
 * 	    http://www.apache.org/licenses/LICENSE-2.0
 * 	
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 *******************************************************************************/
package omelet.driver;

import org.testng.ISuite;
import org.testng.ISuiteListener;

/***
 * For Terminating BrowserStack tunnel after Suite has been executed
 * 
 * @author kapilA
 * 
 */
public class SuiteConfiguration implements ISuiteListener {

	public static String suiteName;

	@Override
	public void onStart(ISuite suite) {
		Logo.getInstance().printLogoAndVersion();
		suiteName = suite.getName();
		//ReportNG property 
		System.setProperty("org.uncommons.reportng.coverage-report", "true");
		final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
		System.setProperty(ESCAPE_PROPERTY, "false");
	}

	@Override
	public void onFinish(ISuite suite) {
		DriverManager.tearDown();
		// TODO Auto-generated method stub	
	}
}
