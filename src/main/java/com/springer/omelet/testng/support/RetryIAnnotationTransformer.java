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
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IAnnotationTransformer2;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

/***
 * For Appending Retry Annotation on All Test Cases and creating Map of classes
 * which are having @AfterMethod and @BeforeMethod
 * 
 * @author kapilA
 * 
 */
public class RetryIAnnotationTransformer implements IAnnotationTransformer,
		IAnnotationTransformer2 {
	private static final Logger LOGGER = Logger
			.getLogger(RetryIAnnotationTransformer.class);

	public static Set<String> beforeMethodClasses = new HashSet<String>();
	public static Set<String> afterMethodClasses = new HashSet<String>();

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

}
