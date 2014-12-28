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
package com.springer.omelet.data.driverconf;

import java.util.List;

public interface IBrowserConf {

	public String getBrowser();

	public String getBrowserVersion();

	public String getOsName();

	public String getOsVersion();

	public boolean isBrowserStackSwitch();

	public String getBsUserName();

	public String getBsPassword();

	public boolean isBsLocalTesting();

	public List<String> getBsURLs();

	public boolean isRemoteFlag();

	public String getRemoteURL();

	public Integer getDriverTimeOut();

	public String getDevice();

	public String getPlatform();

	public Integer getRetryFailedTestCaseCount();

	public String getLocalIEServerPath();

	public String getLocalChromeServerPath();

	public boolean isHighLightElementFlag();

	public boolean isScreenShotFlag();

	public boolean isMobileTest();
	
	public String getDataSource();

}
