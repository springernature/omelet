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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.springer.omelet.testng.support.RetryIAnnotationTransformer;

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

	private static final Logger LOGGER = Logger.getLogger(DataProvider.class);

	private static String getFullMethodName(Method m){
		return m.getDeclaringClass().getName() + "." + m.getName();
	}
	
	@org.testng.annotations.DataProvider(name = "GoogleData", parallel = true)
	public static Object[][] googleSheetDataProvider(Method m){
		String methodName = getFullMethodName(m);
		return getData(methodName);
	}
	
	@org.testng.annotations.DataProvider(name = "XmlData", parallel = true)
	public static Object[][] xmlDataProvider(Method m){
		String methodName = getFullMethodName(m);
		return getData(methodName);
	}
	

	/***
	 * Removes duplicate browsers and prepare data based on the MapStrategy
	 * @param methodName
	 * @return
	 */
	public static Object[][] getData(String methodName) {
		Object[][] returnObject = null;
		List<IBrowserConf> n_browserConf = RetryIAnnotationTransformer.methodBrowser.get(methodName);
		// removing the duplicate via hashset
		Set<IBrowserConf> browserConfSet = new HashSet<IBrowserConf>(
				n_browserConf);
		List<IBrowserConf> browserConf = new ArrayList<IBrowserConf>(
				browserConfSet);
		List<IProperty> prop = RetryIAnnotationTransformer.methodData.get(methodName);
		mapStrategy strategy = RetryIAnnotationTransformer.runStrategy.get(methodName);
		int browserConfsize = browserConf.size();
		int propSize = prop.size();
		int loopCombination;
		int k = 0;
		switch (strategy) {
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
