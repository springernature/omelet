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
package com.springer.omelet.testng.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IAnnotationTransformer2;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

import com.springer.omelet.data.DataProvider.mapStrategy;
import com.springer.omelet.data.IMappingData;
import com.springer.omelet.data.IProperty;
import com.springer.omelet.data.PrettyMessage;
import com.springer.omelet.data.RefineMappedData;
import com.springer.omelet.data.driverconf.IBrowserConf;
import com.springer.omelet.data.googlesheet.GoogleSheetConstant;
import com.springer.omelet.data.googlesheet.ReadGoogle;
import com.springer.omelet.data.xml.BrowserXmlParser;
import com.springer.omelet.data.xml.MappingParserRevisit;
import com.springer.omelet.data.xml.XmlApplicationData;
import com.springer.omelet.exception.FrameworkException;

/***
 * For Appending Retry Annotation on All Test Cases and creating Map of classes
 * which are having @AfterMethod and @BeforeMethod
 * 
 * @author kapilA
 * 
 */
public class RetryIAnnotationTransformer implements IAnnotationTransformer,
		IAnnotationTransformer2, IMethodInterceptor {
	private static final Logger LOGGER = Logger
			.getLogger(RetryIAnnotationTransformer.class);
	private static boolean testDataPrepared = false;

	public static Set<String> beforeMethodClasses = new HashSet<String>();
	public static Set<String> afterMethodClasses = new HashSet<String>();
	public static Map<String, List<IProperty>> methodData = new HashMap<String, List<IProperty>>();
	public static Map<String, List<IBrowserConf>> methodBrowser = new HashMap<String, List<IBrowserConf>>();
	public static Map<String,mapStrategy> runStrategy = new HashMap<String, mapStrategy>();
	@SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {
		if (testMethod != null) {
			if (annotation.getRetryAnalyzer() == null) {
				annotation.setRetryAnalyzer(RetryAnalyzer.class);
				LOGGER.debug("Setting Retry Analyzer for Method:"
						+ testMethod.getName());
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void transform(IConfigurationAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {
		if (testMethod != null) {
			if (testMethod
					.getAnnotation(org.testng.annotations.BeforeMethod.class) != null)
				beforeMethodClasses.add(testMethod.getDeclaringClass()
						.getName());
			if (testMethod
					.getAnnotation(org.testng.annotations.AfterMethod.class) != null)
				afterMethodClasses
						.add(testMethod.getDeclaringClass().getName());
		}

	}

	public void transform(IDataProviderAnnotation annotation, Method method) {

	}

	public void transform(IFactoryAnnotation annotation, Method method) {

	}

	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods,
			ITestContext context) {
		if(!testDataPrepared){
		PrettyMessage prettyMessage = new PrettyMessage();
		Thread t = new Thread(prettyMessage);
		t.start();
		String evironment = System.getProperty("env-type");
		// here we can check if the method have any DataProvider
		for (IMethodInstance method : methods) {
			String dataProviderName = method.getMethod()
					.getConstructorOrMethod().getMethod()
					.getAnnotation(org.testng.annotations.Test.class)
					.dataProvider();
			Method methodReflect = method.getMethod().getConstructorOrMethod()
					.getMethod();
			if (dataProviderName.equals("GoogleData")) {
				updateGooglSheet(methodReflect, evironment);
			} else if (dataProviderName.equals("XmlData")) {
				updateXml(methodReflect, evironment);
			}
		}
		prettyMessage.swtichOffLogging();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testDataPrepared = true;
		}
		return methods;
	}

	private void checkGoogleUserNameAndPassword(String methodName) {
		if (StringUtils.isBlank(System.getProperty(GoogleSheetConstant.GOOGLEUSERNAME))
				&& StringUtils.isBlank(System.getProperty(GoogleSheetConstant.GOOGLEPASSWD))
				&& StringUtils.isBlank(System.getProperty(GoogleSheetConstant.GOOGLESHEETNAME))) {
			throw new FrameworkException(
					"Method with name:"
							+ methodName
							+ "required Google Sheet as Test Data , please provide arguments -DgoogleUsername and -DgoogelPassword");
		}
	}

	private void updateGooglSheet(Method method, String environment) {
		String methodName = method.getDeclaringClass().getName() + "."
				+ method.getName();
		checkGoogleUserNameAndPassword(methodName);
		//System.out.println(System.getProperty(googleUsername));
		ReadGoogle readGoogle = new ReadGoogle(
				System.getProperty(GoogleSheetConstant.GOOGLEUSERNAME),
				System.getProperty(GoogleSheetConstant.GOOGLEPASSWD),
				System.getProperty(GoogleSheetConstant.GOOGLESHEETNAME));
		RefineMappedData refinedData = new RefineMappedData(readGoogle);
		IMappingData mapData = refinedData.getMethodData(method);
		methodBrowser.put(methodName,
				readGoogle.getBrowserListForSheet(mapData));
		methodData.put(methodName,
				readGoogle.getMethodData(environment, mapData));
		runStrategy.put(methodName, mapData.getRunStartegy());
	}

	private void updateXml(Method method, String environment) {
		String methodName = method.getDeclaringClass().getName() + "."
				+ method.getName();
		MappingParserRevisit mpr = new MappingParserRevisit("Mapping.xml");
		RefineMappedData refinedMappedData = new RefineMappedData(mpr);
		IMappingData mapD = refinedMappedData.getMethodData(method);
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
		runStrategy.put(methodName, mapD.getRunStartegy());
	}

}
