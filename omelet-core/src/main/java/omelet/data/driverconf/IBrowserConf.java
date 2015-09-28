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
package omelet.data.driverconf;

import org.openqa.selenium.remote.DesiredCapabilities;

public interface IBrowserConf {

	public String getBrowser();

	public String getuserName();

	public String getKey();
	
	public String host();
	
	public String port();

	public boolean isRemoteFlag();

	public Integer getDriverTimeOut();

	public Integer getRetryFailedTestCaseCount();

	public String getLocalIEServerPath();

	public String getLocalChromeServerPath();

	public boolean isHighLightElementFlag();

	public boolean isScreenShotFlag();
	
	public String getDataSource();
	
	public DesiredCapabilities getCapabilities();
	
	public void updateCapabilities(DesiredCapabilities dc);
	

}
