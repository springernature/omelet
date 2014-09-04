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
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(DataProvider.class);

	@org.testng.annotations.DataProvider(name = "Data", parallel = true)
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
