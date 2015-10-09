/*******************************************************************************
 * Copyright 2014 Springer Science+Business Media Deutschland GmbH
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package omelet.data;

import omelet.common.Utils;
import omelet.configuration.LoadCustomProperties;
import omelet.data.driverconf.IBrowserConf;
import omelet.data.driverconf.PrepareDriverConf;
import omelet.exception.FrameworkException;
import omelet.testng.support.MethodContextCollection;
import omelet.testng.support.RetryIAnnotationTransformer;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.*;

/***
 * Data Provider class for the @Test Methods
 *
 * @author kapilA
 */
public class DataProvider {
	private static final Logger LOGGER = Logger.getLogger(DataProvider.class);

	public enum mapStrategy {
		Full, Optimal
	}

	private static void updateMethodContextHolder(Method m, IBrowserConf browserConf) {

		if (m.getName() != null) {
			MethodContext context = ((MethodContext) MethodContextCollection.getMethodContext(Utils.getFullMethodName
					(m)));

			if (browserConf != null) {
				List<IBrowserConf> ibrowserConfList = new ArrayList<IBrowserConf>();
				ibrowserConfList.add(browserConf);
				context.setBrowserConf(ibrowserConfList);
			}
			context.prepareData();
			RetryIAnnotationTransformer.methodContextHolder.replace(Utils.getFullMethodName(m), context);
		}
	}

	@org.testng.annotations.DataProvider(name = "GoogleData", parallel = true)
	public static Object[][] googleSheetDataProvider(Method m) {
		updateMethodContextHolder(m, null);
		String testMethodName = Utils.getFullMethodName(m);
		return getData(testMethodName, null);
	}

	@org.testng.annotations.DataProvider(name = "XmlData", parallel = true)
	public static Object[][] xmlDataProvider(Method m, ITestContext context) {
		updateMethodContextHolder(m, null);
		String methodName = Utils.getFullMethodName(m);

		return getData(methodName, null);
	}

	@org.testng.annotations.DataProvider(name = "XmlSuiteData", parallel = true)
	public static Object[][] xmlSuiteDataProvider(Method m, ITestContext context) {

		String methodName = Utils.getFullMethodName(m);

		LoadCustomProperties customProperties = new LoadCustomProperties(Utils.getResources(DataProvider.class,
																							"BrowserDC.properties"));
		List<String> browserDCs = customProperties.getCustomProperties();

		Map<String, String> map = new HashMap<String, String>();
		for (String prop : browserDCs) {
			map.put(prop, context.getSuite().getParameter(prop));
		}

		PrepareDriverConf pdc = new PrepareDriverConf(map);
		pdc.refineBrowserValues();
		updateMethodContextHolder(m, pdc.get());
		return getData(methodName, pdc.get());
	}

	public static List<IBrowserConf> filterSameBrowsers(List<IBrowserConf> fullBrowserList) {
		Set<IBrowserConf> browserConfSet = new HashSet<IBrowserConf>(fullBrowserList);
		return new ArrayList<IBrowserConf>(browserConfSet);
	}

	/***
	 * Removes duplicate browsers and prepare data based on the MapStrategy
	 *
	 * @param methodName
	 * @return
	 */
	public static Object[][] getData(String methodName, IBrowserConf iBrowserConf) {
		Object[][] testMethodData = null;
		List<IBrowserConf> browserConfFilteredList;

		if (MethodContextCollection.getMethodContext(methodName).getBrowserConf() == null) {
			browserConfFilteredList = new ArrayList<IBrowserConf>();
			browserConfFilteredList.add(iBrowserConf);
		} else {
			browserConfFilteredList = filterSameBrowsers(MethodContextCollection.getMethodContext(methodName).
					getBrowserConf());
		}
		List<IProperty> testMData = MethodContextCollection.getMethodContext(methodName).getMethodTestData();
		mapStrategy strategy = MethodContextCollection.getMethodContext(methodName).getRunStrategy();
		int browserConfCount = browserConfFilteredList.size();
		int testDataCount = testMData.size();
		verifyCount(browserConfCount, "IBrowserConf");
		verifyCount(testDataCount, "IProperty");
		int loopCombination;
		int k = 0;
		switch (strategy) {
			case Full:
				loopCombination = browserConfCount * testDataCount;
				testMethodData = new Object[loopCombination][2];

				for (int i = 0; i < browserConfCount; i++) {
					for (int j = 0; j < testDataCount; j++) {
						testMethodData[k][0] = browserConfFilteredList.get(i);
						testMethodData[k][1] = testMData.get(j);
						k++;
					}
				}
				break;
			case Optimal:
			/*if(testDataCount <=0)
				testDataCount = 1;*/
				if (browserConfCount >= testDataCount) {
					loopCombination = browserConfCount;
				} else {
					loopCombination = testDataCount;
				}
				testMethodData = new Object[loopCombination][2];
				for (int i = 0; i < loopCombination; i++) {
					Random r = new Random();
					// check whose value is greater and start the loop
					if (i >= browserConfCount) {
						testMethodData[i][0] = browserConfFilteredList.get(r
																				   .nextInt(browserConfCount));
					} else {
						testMethodData[i][0] = browserConfFilteredList.get(i);
					}
					if (i >= testDataCount) {
						testMethodData[i][1] = testMData.get(r
																	 .nextInt(testDataCount));
					} else {
						testMethodData[i][1] = testMData.get(i);
					}
				}
				break;
			default:
				break;
		}
		return testMethodData;
	}

	private static void verifyCount(int count, String dataName) {
		if (count <= 0) {
			throw new FrameworkException("Data Provider of Type:" + dataName + "not present , there is some problem " +
												 "please check!");
		}
	}
}