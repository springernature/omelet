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
import java.util.List;
import java.util.Map;

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

import com.springer.omelet.data.IMappingData;
import com.springer.omelet.data.MethodContext;
import com.springer.omelet.data.PrettyMessage;
import com.springer.omelet.data.RefineMappedData;
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
	//private MethodContextCollection methodContextCollection = MethodContextCollection.getInstance();
	protected static final Map<String,MethodContext> methodContextHolder = new HashMap<String, MethodContext>();
	
	@SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod){
		
		if(testMethod != null)
		{
			MethodContext context = new MethodContext(testMethod);
			context.setRetryAnalyser(annotation);
			context.setDataProvider(annotation, testMethod);
			//update methodContextCollection
			//methodContextCollection.updateMethodContext(getFullMethodName(testMethod), context);
			methodContextHolder.put(getFullMethodName(testMethod), context);
		}
		
	}
	
	private static String getFullMethodName(Method m) {
		return m.getDeclaringClass().getName() + "." + m.getName();
	}

	@SuppressWarnings("rawtypes")
	public void transform(IConfigurationAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {
		
	}

	public void transform(IDataProviderAnnotation annotation, Method method) {
		//TODO WHY?? Abstract?
		//No need to add any implementation as we are overriding testng interface
	}

	public void transform(IFactoryAnnotation annotation, Method method) {
		//TODO WHY?? Abstract?
		//No need to add any implementation as we are overriding testng interface
	}

	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods,
			ITestContext context) {
		
			PrettyMessage prettyMessage = new PrettyMessage();
			Thread t = new Thread(prettyMessage);
			t.start();
			// here we can check if the method have any DataProvider
			for (IMethodInstance method : methods) {	
				Method methodReflect = method.getMethod()
						.getConstructorOrMethod().getMethod();
				switch(methodContextHolder.get(getFullMethodName(methodReflect)).getDataProvider()){
				case GoogleData:
					updateGoogleSheet(methodReflect, System.getProperty("env-type"), methodContextHolder.get(getFullMethodName(methodReflect)));
					break;
				case XmlData:
					updateXml(methodReflect, System.getProperty("env-type"), methodContextHolder.get(getFullMethodName(methodReflect)));
					break;
					default:
						break;
				}
				//methodContextCollection.getMethodContext(getFullMethodName(methodReflect))
			}
			prettyMessage.swtichOffLogging();
			try {
				t.join();
			} catch (InterruptedException e) {
				LOGGER.error(e);
			}
		return methods;
	}
	
	private void checkGoogleUserNameAndPassword(String methodName) {
		if (StringUtils.isBlank(System
				.getProperty(GoogleSheetConstant.GOOGLEUSERNAME))
				&& StringUtils.isBlank(System
						.getProperty(GoogleSheetConstant.GOOGLEPASSWD))
				&& StringUtils.isBlank(System
						.getProperty(GoogleSheetConstant.GOOGLESHEETNAME))) {
			//This is not the solution as TestNG is not logging the exception hence setting it here
			LOGGER.info("Method with name:"
							+ methodName
							+ "required Google Sheet as Test Data , please provide arguments -DgoogleUsername and -DgoogelPassword");
			throw new FrameworkException(
					"Method with name:"
							+ methodName
							+ "required Google Sheet as Test Data , please provide arguments -DgoogleUsername and -DgoogelPassword");
		}
	}
    
	private void updateGoogleSheet(Method method, String environment,MethodContext methodContext) {
		String methodName = method.getDeclaringClass().getName() + "."
				+ method.getName();
		checkGoogleUserNameAndPassword(methodName);
		// System.out.println(System.getProperty(googleUsername));
		ReadGoogle readGoogle = new ReadGoogle(
				System.getProperty(GoogleSheetConstant.GOOGLEUSERNAME),
				System.getProperty(GoogleSheetConstant.GOOGLEPASSWD),
				System.getProperty(GoogleSheetConstant.GOOGLESHEETNAME));
		RefineMappedData refinedData = new RefineMappedData(readGoogle);
		IMappingData mapData = refinedData.getMethodData(method);
		methodContext.setBrowserConf(readGoogle.getBrowserListForSheet(mapData));
		methodContext.setTestData(readGoogle.getMethodData(environment, mapData));
        methodContext.setRunStrategy(mapData.getRunStartegy());

	}
	
	private void updateXml(Method method, String environment,MethodContext methodContext) {
		MappingParserRevisit mpr = new MappingParserRevisit("Mapping.xml");
		RefineMappedData refinedMappedData = new RefineMappedData(mpr);
		IMappingData mapD = refinedMappedData.getMethodData(method);
		XmlApplicationData xmlapData = null;
		if (environment != null && !StringUtils.isBlank(environment)) {
			// get the xml name from MappingParser Static Method
			xmlapData = new XmlApplicationData(mapD.getTestData(), environment);
			methodContext.setTestData(xmlapData.getAppData()); 
		} else {
			xmlapData = new XmlApplicationData(mapD.getTestData());
			methodContext.setTestData(xmlapData.getAppData());
		}
		BrowserXmlParser bxp = new BrowserXmlParser(mapD.getClientEnvironment());		 
		methodContext.setBrowserConf(bxp.getBrowserConf());
		methodContext.setRunStrategy(mapD.getRunStartegy());
	}
}