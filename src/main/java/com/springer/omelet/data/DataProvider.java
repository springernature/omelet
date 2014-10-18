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
package com.springer.omelet.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/***
 * Data Provider class for the @Test Methods
 * 
 * @author kapilA
 * 
 */
public class DataProvider {

	public static enum mapStrategy {
		Full, Optimal
	};

	static Map<String, List<IProperty>> methodData = Collections
			.synchronizedMap(new HashMap<String, List<IProperty>>());
	static Map<String, List<IBrowserConf>> methodBrowser = Collections
			.synchronizedMap(new HashMap<String, List<IBrowserConf>>());
	public static final String GOOGLEDATANAME="GoogleSheet";
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(DataProvider.class);

	//@org.testng.annotations.DataProvider(name = "DataT", parallel = true)
	public static Object[][] dataProvider(Method m) {
		String environment = System.getProperty("env-type");

		String methodName = m.getDeclaringClass().getName() + "." + m.getName();
		IMappingData mapD = MappingParser.getInstance().getMethodData(m);
		XmlApplicationData xmlapData = null;
		if (environment != null && !StringUtils.isBlank(environment)) {
			// get the xml name from MappingParser Static Method
			xmlapData = new XmlApplicationData(mapD.getTestData(), environment);
			methodData.put(methodName, xmlapData.getAppData());
		} else {
			xmlapData = new XmlApplicationData(mapD.getTestData());
			methodData.put(methodName, xmlapData.getAppData());
		}
		BrowserXmlParser bxp = new BrowserXmlParser(mapD.getClientEnvironment());
		methodBrowser.put(methodName, bxp.getBrowserConf());
		return getData(mapD.getRunStartegy(), methodName);
	}
	
	private static String getEnvironment(){
		return System.getProperty("env-type");
	}
	
	private static String getFullMethodName(Method m){
		return m.getDeclaringClass().getName() + "." + m.getName();
	}
	
	@org.testng.annotations.DataProvider(name = GOOGLEDATANAME, parallel = true)
	public static Object[][] googleSheetDataProvider(Method m){
		String environment = getEnvironment();
		String methodName = getFullMethodName(m);
		//Check if we already have values in methodBrowser and methodData which for sure we will be having 
		//simply call getData which is having the logic of filtering all the data for you 
		//Challenge is we should not do expensive call to sheet 
		//And methodBrowserShould be having values for the data for Google sheet if at all it is required
		return null;
	}
	
	@org.testng.annotations.DataProvider(name ="Data",parallel = true)
	public static Object[][] xmlDataRevisited(Method m){
		String environment = System.getProperty("env-type");
		String methodName = m.getDeclaringClass().getName() + "." + m.getName();
		MappingParserRevisit mpr = new MappingParserRevisit("Mapping.xml");
		RefineMappedData refinedMappedData = new RefineMappedData(mpr);
		IMappingData mapD = refinedMappedData.getMethodData(m);
		System.out.println(mapD.getClientEnvironment().get(0));
		System.out.println(mapD.getTestData());
		XmlApplicationData xmlapData = null;
		if (environment != null && !StringUtils.isBlank(environment)) {
			// get the xml name from MappingParser Static Method
			xmlapData = new XmlApplicationData(mapD.getTestData(), environment);
			methodData.put(methodName, xmlapData.getAppData());
		} else {
			xmlapData = new XmlApplicationData(mapD.getTestData());
			methodData.put(methodName, xmlapData.getAppData());
		}
		BrowserXmlParser bxp = new BrowserXmlParser(mapD.getClientEnvironment());
		methodBrowser.put(methodName, bxp.getBrowserConf());
		return getData(mapD.getRunStartegy(), methodName);
	}

	public static Object[][] getData(mapStrategy startegy, String methodName) {
		Object[][] returnObject = null;
		List<IBrowserConf> n_browserConf = methodBrowser.get(methodName);
		// removing the duplicate via hashset
		Set<IBrowserConf> browserConfSet = new HashSet<IBrowserConf>(
				n_browserConf);
		List<IBrowserConf> browserConf = new ArrayList<IBrowserConf>(
				browserConfSet);
		List<IProperty> prop = methodData.get(methodName);
		int browserConfsize = browserConf.size();
		int propSize = prop.size();
		int loopCombination;
		int k = 0;
		switch (startegy) {
		case Full:
			loopCombination = browserConfsize * propSize;
			returnObject = new Object[loopCombination][2];

			for (int i = 0; i < browserConfsize; i++) {

				for (int j = 0; j < propSize; j++) {
					returnObject[k][0] = browserConf.get(i);
					returnObject[k][1] = prop.get(j);
					k++;
				}
			}
			break;
		case Optimal:
			if (browserConfsize >= propSize)
				loopCombination = browserConfsize;
			else
				loopCombination = propSize;
			returnObject = new Object[loopCombination][2];
			for (int i = 0; i < loopCombination; i++) {
				Random r = new Random();
				// check whose value is greater and start the loop
				if (i >= browserConfsize) {
					returnObject[i][0] = browserConf.get(r
							.nextInt(browserConfsize));
				} else {
					returnObject[i][0] = browserConf.get(i);
				}
				if (i >= propSize) {
					returnObject[i][1] = prop.get(r.nextInt(propSize));
				} else {
					returnObject[i][1] = prop.get(i);
				}

			}
			break;
		default:
			break;
		}
		return returnObject;
	}

}
