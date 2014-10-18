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

import com.springer.omelet.data.IBrowserConf;
import com.springer.omelet.data.IMappingData;
import com.springer.omelet.data.IProperty;
import com.springer.omelet.data.RefineMappedData;
import com.springer.omelet.data.googlesheet.ReadGoogle;
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

	public static Set<String> beforeMethodClasses = new HashSet<String>();
	public static Set<String> afterMethodClasses = new HashSet<String>();
	public static final String googleUsername = "googleUserName";
	public static final String googlePassword = "googlePassword";
	

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
		Map<String,List<IProperty>> methodTestDataList = new HashMap<String, List<IProperty>>();
		Map<String,List<IBrowserConf>> methodBrowserConfList = new HashMap<String, List<IBrowserConf>>();
		// here we can check if the method have any DataProvider
		for (IMethodInstance method : methods) {
			String dataProviderName = method.getMethod().getConstructorOrMethod().getMethod()
					.getAnnotation(org.testng.annotations.Test.class)
					.dataProvider();
			String methodName = method.getMethod().getConstructorOrMethod().getMethod().getDeclaringClass().getName() + "." + method.getMethod().getConstructorOrMethod().getMethod().getName();
			if(dataProviderName.equals("GoogleSheet")){
				checkGoogleUserNameAndPassword(methodName);
				ReadGoogle readGoogle = new ReadGoogle(System.getProperty(googleUsername), System.getProperty(googlePassword), "Mapping");
				RefineMappedData refinedData = new RefineMappedData(readGoogle);
				IMappingData mapData = refinedData.getMethodData(method.getMethod().getConstructorOrMethod().getMethod());
				methodBrowserConfList.put(methodName,readGoogle.getBrowserListForSheet(mapData));
				methodTestDataList.put(methodName,readGoogle.getMethodData(System.getProperty("env-type"), mapData));
			}
		}
		return methods;
	}
	
	private void checkGoogleUserNameAndPassword(String methodName){
		if(StringUtils.isBlank(System.getProperty(googleUsername)) && StringUtils.isBlank(System.getProperty(googlePassword))){
			throw new FrameworkException("Method with name:"+methodName+"required Google Sheet as Test Data , please provide arguments -DgoogleUsername and -DgoogelPassword");
		}
	}

}
