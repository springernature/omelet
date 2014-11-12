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
package com.springer.omelet.test.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.springer.omelet.data.BrowserConfiguration;
import com.springer.omelet.data.BrowserStackConstant;
import com.springer.omelet.data.IBrowserConf;

public class BrowserConfIsEqualTest {

	private HashMap<String, String> browserConfValues1 = new HashMap<String, String>();
	private HashMap<String, String> browserConfValues2 = new HashMap<String, String>();
	List<IBrowserConf> browserConfList = new ArrayList<IBrowserConf>();

	@Test(enabled = true, description = "same browser -->remote flag off-->filtering using set and List")
	public void checkBrowserConfEquality_RemoteFlagOFF() {
		browserConfValues1.clear();
		browserConfValues2.clear();
		browserConfValues1.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues1.put(BrowserStackConstant.remoteFlag.toString(), "false");

		browserConfValues2.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues2.put(BrowserStackConstant.remoteFlag.toString(), "false");
		BrowserConfiguration obj1 = new BrowserConfiguration(browserConfValues1);
		BrowserConfiguration obj2 = new BrowserConfiguration(browserConfValues2);

		browserConfList.add(obj1);
		browserConfList.add(obj2);
		Assert.assertEquals(browserConfList.size(), 2);
		Set<IBrowserConf> s = new HashSet<IBrowserConf>(browserConfList);
		List<IBrowserConf> newList = new ArrayList<IBrowserConf>(s);
		Assert.assertEquals(obj1, obj2);
		Assert.assertEquals(s.size(), 1);
		Assert.assertEquals(newList.size(), 1);
	}

	@Test(description = "Remote Flag on and required values equal", enabled = true)
	public void remoteFlagOn() {
		browserConfValues1.clear();
		browserConfValues2.clear();

		browserConfValues1.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues1.put(BrowserStackConstant.remoteFlag.toString(), "true");
		browserConfValues1.put(BrowserStackConstant.bsSwitch.toString(), "true");
		browserConfValues1.put(BrowserStackConstant.browserVersion.toString(), "11");
		browserConfValues1.put(BrowserStackConstant.os.toString(), "Windows");
		browserConfValues1.put(BrowserStackConstant.osVersion.toString(), "XP");

		browserConfValues2.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues2.put(BrowserStackConstant.remoteFlag.toString(), "true");
		browserConfValues2.put(BrowserStackConstant.bsSwitch.toString(), "true");
		browserConfValues2.put(BrowserStackConstant.browserVersion.toString(), "11");
		browserConfValues2.put(BrowserStackConstant.os.toString(), "Windows");
		browserConfValues2.put(BrowserStackConstant.osVersion.toString(), "XP");

		BrowserConfiguration obj1 = new BrowserConfiguration(browserConfValues1);
		BrowserConfiguration obj2 = new BrowserConfiguration(browserConfValues2);
		Assert.assertEquals(obj1, obj2);
	}

	@Test(description = "Remote Flag on and required values equal and not required not equal", enabled = true)
	public void remoteFlagOn_2() {
		browserConfValues1.clear();
		browserConfValues2.clear();

		browserConfValues1.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues1.put(BrowserStackConstant.remoteFlag.toString(), "true");
		browserConfValues1.put(BrowserStackConstant.bsSwitch.toString(), "true");
		browserConfValues1.put(BrowserStackConstant.browserVersion.toString(), "11");
		browserConfValues1.put(BrowserStackConstant.os.toString(), "Windows");
		browserConfValues1.put(BrowserStackConstant.osVersion.toString(), "XP");
		browserConfValues1.put(BrowserStackConstant.bs_localTesting.toString(),
				"true");

		browserConfValues2.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues2.put(BrowserStackConstant.remoteFlag.toString(), "true");
		browserConfValues2.put(BrowserStackConstant.bsSwitch.toString(), "true");
		browserConfValues2.put(BrowserStackConstant.browserVersion.toString(), "11");
		browserConfValues2.put(BrowserStackConstant.os.toString(), "Windows");
		browserConfValues2.put(BrowserStackConstant.osVersion.toString(), "XP");
		browserConfValues2.put(BrowserStackConstant.bs_localTesting.toString(),
				"false");

		BrowserConfiguration obj1 = new BrowserConfiguration(browserConfValues1);
		BrowserConfiguration obj2 = new BrowserConfiguration(browserConfValues2);
		Assert.assertEquals(obj1, obj2);
		Assert.assertEquals(obj1.hashCode(), obj2.hashCode());
	}

	@Test(description = "Remote Flag on and required values not equal and not required not equal")
	public void remoteFlagOn_3() {
		browserConfValues1.clear();
		browserConfValues2.clear();

		browserConfValues1.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues1.put(BrowserStackConstant.remoteFlag.toString(), "true");
		browserConfValues1.put(BrowserStackConstant.bsSwitch.toString(), "true");
		browserConfValues1.put(BrowserStackConstant.browserVersion.toString(), "11");
		browserConfValues1.put(BrowserStackConstant.os.toString(), "Mac");
		browserConfValues1.put(BrowserStackConstant.osVersion.toString(), "XP");
		browserConfValues1.put(BrowserStackConstant.bs_localTesting.toString(),
				"true");

		browserConfValues2.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues2.put(BrowserStackConstant.remoteFlag.toString(), "true");
		browserConfValues2.put(BrowserStackConstant.bsSwitch.toString(), "true");
		browserConfValues2.put(BrowserStackConstant.browserVersion.toString(), "11");
		browserConfValues2.put(BrowserStackConstant.os.toString(), "Windows");
		browserConfValues2.put(BrowserStackConstant.osVersion.toString(), "XP");
		browserConfValues2.put(BrowserStackConstant.bs_localTesting.toString(),
				"false");

		BrowserConfiguration obj1 = new BrowserConfiguration(browserConfValues1);
		BrowserConfiguration obj2 = new BrowserConfiguration(browserConfValues2);
		List<IBrowserConf> testL = new ArrayList<IBrowserConf>();
		testL.add(obj1);
		testL.add(obj2);
		Assert.assertEquals(testL.size(), 2);
		Set<IBrowserConf> s = new HashSet<IBrowserConf>(testL);
		Assert.assertEquals(s.size(), 2);
		Assert.assertNotEquals(obj1, obj2);
		Assert.assertNotEquals(obj1.hashCode(), obj2.hashCode());
	}

	@Test(description = "remote Flag on ->bsswitch off-->browser equals")
	public void remoteFlag_4() {

		browserConfValues1.clear();
		browserConfValues2.clear();

		browserConfValues1.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues1.put(BrowserStackConstant.remoteFlag.toString(), "true");
		browserConfValues1.put(BrowserStackConstant.bsSwitch.toString(), "false");
		browserConfValues1.put(BrowserStackConstant.browserVersion.toString(), "11");
		browserConfValues1.put(BrowserStackConstant.os.toString(), "Mac");
		browserConfValues1.put(BrowserStackConstant.osVersion.toString(), "XP");
		browserConfValues1.put(BrowserStackConstant.bs_localTesting.toString(),
				"true");

		browserConfValues2.put(BrowserStackConstant.browserName.toString(),
				"firefox");
		browserConfValues2.put(BrowserStackConstant.remoteFlag.toString(), "true");
		browserConfValues2.put(BrowserStackConstant.bsSwitch.toString(), "false");
		browserConfValues2.put(BrowserStackConstant.browserVersion.toString(), "11");
		browserConfValues2.put(BrowserStackConstant.os.toString(), "Windows");
		browserConfValues2.put(BrowserStackConstant.osVersion.toString(), "XP");
		browserConfValues2.put(BrowserStackConstant.bs_localTesting.toString(),
				"false");

		BrowserConfiguration obj1 = new BrowserConfiguration(browserConfValues1);
		BrowserConfiguration obj2 = new BrowserConfiguration(browserConfValues2);
		Assert.assertEquals(obj1, obj2);
		Assert.assertEquals(obj1.hashCode(), obj2.hashCode());

	}

}
