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
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.springer.omelet.data.BrowserConstant;
import com.springer.omelet.data.BrowserXmlParser;
import com.springer.omelet.data.IBrowserConf;

public class BrowserXmlParserTest {

	@BeforeMethod
	public void beforeTest() {

		System.setProperty(BrowserConstant.browserName.toString(), "");
		System.setProperty(BrowserConstant.browserVersion.toString(), "");
		System.setProperty(BrowserConstant.bs_key.toString(), "");
	}

	@Test
	public void verifyBrowserConf_singleXml() {
		List<String> xmlName = new ArrayList<String>();
		xmlName.add("BrowseXmlP_2.xml");
		BrowserXmlParser bxp = new BrowserXmlParser(xmlName);
		List<IBrowserConf> browserIs = bxp.getBrowserConf();
		Assert.assertEquals(browserIs.size(), 1);
		IBrowserConf browserI = browserIs.get(0);
		Assert.assertEquals("firefox", browserI.getBrowser());
		Assert.assertEquals("25", browserI.getBrowserVersion());
		Assert.assertEquals("testkey", browserI.getBsPassword());
		Assert.assertEquals("https:test1", browserI.getBsURLs().get(0));
		Assert.assertEquals("https:test2", browserI.getBsURLs().get(1));
		Assert.assertEquals("testusername", browserI.getBsUserName());
		Assert.assertEquals("None", browserI.getDevice());
		Assert.assertEquals("WINDOWS", browserI.getOsName());
		Assert.assertEquals("xp", browserI.getOsVersion());
		Assert.assertEquals("WINDOWS", browserI.getPlatform());
		Assert.assertTrue(browserI.isRemoteFlag());
		Assert.assertFalse(browserI.isBrowserStackSwitch());
		Assert.assertEquals("http:testRemoteURL", browserI.getRemoteURL());
		Assert.assertEquals(Integer.valueOf("10"), browserI.getDriverTimeOut());
		/*
		 * browserI.getDriverTimeOut() browserI.getOsName()
		 * browserI.getOsVersion() browserI.getPlatform()
		 */
	}

	@Test
	public void verifySingleXMLMultiBrowser() {
		List<String> xmlName = new ArrayList<String>();
		xmlName.add("BrowseXmlP_multi_1.xml");
		BrowserXmlParser bxp = new BrowserXmlParser(xmlName);
		List<IBrowserConf> browserConfList = bxp.getBrowserConf();
		Assert.assertEquals(browserConfList.size(), 2);
		IBrowserConf browserConf1 = browserConfList.get(0);
		IBrowserConf browserConf2 = browserConfList.get(1);
		Assert.assertEquals("firefox", browserConf1.getBrowser());
		Assert.assertEquals("25", browserConf1.getBrowserVersion());
		Assert.assertEquals("testkey", browserConf1.getBsPassword());
		Assert.assertEquals("https:test1", browserConf1.getBsURLs().get(0));
		Assert.assertEquals("https:test2", browserConf1.getBsURLs().get(1));
		Assert.assertEquals("testusername", browserConf1.getBsUserName());
		Assert.assertEquals("None", browserConf1.getDevice());
		Assert.assertEquals("WINDOWS", browserConf1.getOsName());
		Assert.assertEquals("xp", browserConf1.getOsVersion());
		Assert.assertEquals("WINDOWS", browserConf1.getPlatform());
		Assert.assertTrue(browserConf1.isRemoteFlag());
		Assert.assertFalse(browserConf1.isBrowserStackSwitch());
		Assert.assertEquals("http:testRemoteURL", browserConf1.getRemoteURL());
		Assert.assertEquals(Integer.valueOf("10"),
				browserConf1.getDriverTimeOut());

		// Second XML
		Assert.assertEquals("chrome", browserConf2.getBrowser());
		Assert.assertEquals("32", browserConf2.getBrowserVersion());
		Assert.assertEquals("testkey1", browserConf2.getBsPassword());
		Assert.assertEquals("testusername1", browserConf2.getBsUserName());
		Assert.assertEquals("None", browserConf2.getDevice());
		Assert.assertEquals("LINUX", browserConf2.getOsName());
		Assert.assertEquals("15", browserConf2.getOsVersion());
		Assert.assertEquals("LINUX", browserConf2.getPlatform());
		Assert.assertFalse(browserConf2.isRemoteFlag());
		Assert.assertTrue(browserConf2.isBrowserStackSwitch());
		// Assert.assertEquals("http:testRemoteURL",
		// browserConf1.getRemoteURL());
		// Assert.assertEquals(Integer.valueOf("10"),
		// browserConf1.getDriverTimeOut());
	}

	@Test
	public void mutipleXml() {

		List<String> xmlName = new ArrayList<String>();
		xmlName.add("BrowseXmlP_multi_1.xml");
		xmlName.add("BrowseXmlP_2.xml");
		BrowserXmlParser bxp = new BrowserXmlParser(xmlName);
		List<IBrowserConf> browserConfList = bxp.getBrowserConf();
		Assert.assertEquals(browserConfList.size(), 3);
		IBrowserConf browserConf1 = browserConfList.get(0);
		IBrowserConf browserConf2 = browserConfList.get(1);
		IBrowserConf browserConf3 = browserConfList.get(2);
		Assert.assertEquals("firefox", browserConf1.getBrowser());
		Assert.assertEquals("25", browserConf1.getBrowserVersion());
		Assert.assertEquals("testkey", browserConf1.getBsPassword());
		Assert.assertEquals("https:test1", browserConf1.getBsURLs().get(0));
		Assert.assertEquals("https:test2", browserConf1.getBsURLs().get(1));
		Assert.assertEquals("testusername", browserConf1.getBsUserName());
		Assert.assertEquals("None", browserConf1.getDevice());
		Assert.assertEquals("WINDOWS", browserConf1.getOsName());
		Assert.assertEquals("xp", browserConf1.getOsVersion());
		Assert.assertEquals("WINDOWS", browserConf1.getPlatform());
		Assert.assertTrue(browserConf1.isRemoteFlag());
		Assert.assertFalse(browserConf1.isBrowserStackSwitch());
		Assert.assertEquals("http:testRemoteURL", browserConf1.getRemoteURL());
		Assert.assertEquals(Integer.valueOf("10"),
				browserConf1.getDriverTimeOut());

		// Second XML
		Assert.assertEquals("chrome", browserConf2.getBrowser());
		Assert.assertEquals("32", browserConf2.getBrowserVersion());
		Assert.assertEquals("testkey1", browserConf2.getBsPassword());
		Assert.assertEquals("testusername1", browserConf2.getBsUserName());
		Assert.assertEquals("None", browserConf2.getDevice());
		Assert.assertEquals("LINUX", browserConf2.getOsName());
		Assert.assertEquals("15", browserConf2.getOsVersion());
		Assert.assertEquals("LINUX", browserConf2.getPlatform());
		Assert.assertFalse(browserConf2.isRemoteFlag());
		Assert.assertTrue(browserConf2.isBrowserStackSwitch());
		// Assert.assertEquals("http:testRemoteURL",
		// browserConf1.getRemoteURL());
		// Assert.assertEquals(Integer.valueOf("10"),
		// browserConf1.getDriverTimeOut());

		// 3rd XML Browser
		Assert.assertEquals("firefox", browserConf3.getBrowser());
		Assert.assertEquals("25", browserConf3.getBrowserVersion());
		Assert.assertEquals("testkey", browserConf3.getBsPassword());
		Assert.assertEquals("https:test1", browserConf3.getBsURLs().get(0));
		Assert.assertEquals("https:test2", browserConf3.getBsURLs().get(1));
		Assert.assertEquals("testusername", browserConf3.getBsUserName());
		Assert.assertEquals("None", browserConf3.getDevice());
		Assert.assertEquals("WINDOWS", browserConf3.getOsName());
		Assert.assertEquals("xp", browserConf3.getOsVersion());
		Assert.assertEquals("WINDOWS", browserConf3.getPlatform());
		Assert.assertTrue(browserConf3.isRemoteFlag());
		Assert.assertFalse(browserConf3.isBrowserStackSwitch());
		Assert.assertEquals("http:testRemoteURL", browserConf3.getRemoteURL());
		Assert.assertEquals(Integer.valueOf("10"),
				browserConf3.getDriverTimeOut());
	}

}
