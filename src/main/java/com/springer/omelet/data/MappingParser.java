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

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.testng.log4testng.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.springer.omelet.common.Utils;
import com.springer.omelet.driver.SuiteConfiguration;
import com.springer.omelet.exception.FrameworkException;

/***
 * Reads Mapping.xml and return CLient Environment xml names , Test Data xml
 * names , Run Strategy for method, Project name if provided in xml, build
 * number if provided in mapping.xml
 * 
 * @author kapilA
 * 
 */
public class MappingParser {
	private final static Logger LOGGER = Logger.getLogger(MappingParser.class);

	private static MappingParser localInstance = null;
	private final DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
	private DocumentBuilder builder = null;
	private Document document = null;
	private String xmlName = "Mapping.xml";
	private Map<String, List<String>> methodBrowserXml = Collections

	.synchronizedMap(new HashMap<String, List<String>>());
	private Map<String, String> methodDataXml = Collections
			.synchronizedMap(new HashMap<String, String>());
	private Map<String, String> methodRunStartegy = Collections
			.synchronizedMap(new HashMap<String, String>());

	private Map<String, List<String>> classBrowserXml = Collections
			.synchronizedMap(new HashMap<String, List<String>>());
	private Map<String, String> classDataXml = Collections
			.synchronizedMap(new HashMap<String, String>());
	private Map<String, String> classRunStartegy = Collections
			.synchronizedMap(new HashMap<String, String>());

	private Map<String, List<String>> packageBrowserXml = Collections
			.synchronizedMap(new HashMap<String, List<String>>());
	private Map<String, String> packageDataXml = Collections
			.synchronizedMap(new HashMap<String, String>());
	private Map<String, String> packageRunStartegy = Collections
			.synchronizedMap(new HashMap<String, String>());

	private static String projectName;
	private static String buildNumber;

	private MappingParser() {

		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(Utils.getResources(MappingParser.class,
					xmlName));
			setPackageMapping();
		} catch (ParserConfigurationException e) {
			LOGGER.error(e);
		} catch (SAXException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	public static synchronized MappingParser getInstance() {
		if (localInstance == null) {
			localInstance = new MappingParser();
		}
		return localInstance;
	}

	/***
	 * this should not to be called in code ,it is just to facilitate
	 * unit test case for the class
	 * 
	 * @param xmlName
	 */
	public void setXMLNameToParse(String xmlName) {
		this.xmlName = xmlName;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(Utils.getResources(MappingParser.class,
					xmlName));
			setPackageMapping();
		} catch (ParserConfigurationException e) {
			LOGGER.error(e);
		} catch (SAXException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	/***
	 * Reads Mapping.xml and updates all the Map available for
	 * package,class,method as class and methods are called
	 */
	private void setPackageMapping() {

		Element rootElement = document.getDocumentElement();
		setPorjectName(rootElement);
		NodeList nodes = rootElement.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node instanceof Element) {
				Element packageD = (Element) node;
				setBrowserConf(packageBrowserXml, packageD, getName(packageD));
				setTestData(packageDataXml, packageD, getName(packageD));
				setRunStrategy(packageRunStartegy, packageD, getName(packageD));
				updateClassMap(packageD, packageD.getAttribute("name"));
			}
		}
	}

	/***
	 * update class hashMap
	 * 
	 * @param e
	 * @param packageName
	 */

	private void updateClassMap(Element e, String packageName) {
		String className;
		NodeList nodes = e.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node instanceof Element) {
				Element classElement = (Element) node;
				className = packageName + "." + getName(classElement);
				setBrowserConf(classBrowserXml, classElement, className);
				setTestData(classDataXml, classElement, className);
				setRunStrategy(classRunStartegy, classElement, className);
				updateMethodMap(classElement, className);
			}
		}

	}

	/***
	 * update method hashMap
	 * 
	 * @param e
	 * @param packageClassName
	 */
	private void updateMethodMap(Element e, String packageClassName) {
		NodeList nodes = e.getChildNodes();
		String methodName;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node instanceof Element) {

				Element methodElement = (Element) node;
				methodName = packageClassName + "." + getName(methodElement);
				setBrowserConf(methodBrowserXml, methodElement, methodName);
				setTestData(methodDataXml, methodElement, methodName);
				setRunStrategy(methodRunStartegy, methodElement, methodName);
			}
		}
	}

	/***
	 * Return list of String if those are comma seprated else list with single
	 * entry
	 * 
	 * @param commaSepratedList
	 * @return
	 */
	private List<String> getList(String commaSepratedList) {
		List<String> returnedList = new ArrayList<String>();
		if (commaSepratedList.contains(";")) {
			String array[] = commaSepratedList.split(";");
			for (int i = 0; i < array.length; i++)
				returnedList.add(array[i]);
		} else {
			returnedList.add(commaSepratedList);
		}
		return returnedList;
	}

	static enum mappingValue {
		name, testData, clientEnvironment, runStrategy
	};

	/***
	 * Check for the name attribute for tag of mapping.xml , if not
	 * found throw the exception
	 * 
	 * @param element
	 * @return
	 */
	private String getName(Element element) {
		if (element.getAttribute(mappingValue.name.toString()) != null
				&& !element.getAttribute(mappingValue.name.toString())
						.isEmpty()) {
			return element.getAttribute(mappingValue.name.toString());

		} else {
			throw new FrameworkException(
					"name attribute for the element with name"
							+ element.getNodeName()
							+ " in Mapping.xml cannot be null nor empty");
		}
	}

	private void setTestData(Map<String, String> mapToUpdate, Element element,
			String key) {

		if (element.getAttribute(mappingValue.testData.toString()) != null
				&& !element.getAttribute(mappingValue.testData.toString())
						.isEmpty())
			mapToUpdate.put(key,
					element.getAttribute(mappingValue.testData.toString()));
	}

	private void setRunStrategy(Map<String, String> mapToUpdate,
			Element element, String key) {
		if (element.getAttribute(mappingValue.runStrategy.toString()) != null
				&& !element.getAttribute(mappingValue.runStrategy.toString())
						.isEmpty()) {
			mapToUpdate.put(key,
					element.getAttribute(mappingValue.runStrategy.toString()));
		}
	}

	private void setBrowserConf(Map<String, List<String>> mapToUpdate,
			Element element, String key) {
		if (element.getAttribute(mappingValue.clientEnvironment.toString()) != null
				&& !element.getAttribute(
						mappingValue.clientEnvironment.toString()).isEmpty()) {
			mapToUpdate.put(key, getList(element
					.getAttribute(mappingValue.clientEnvironment.toString())));
		}
	}

	private void setPorjectName(Element element) {

		projectName = returnCalcValue("projectName", element);
		if (projectName == null || StringUtils.isBlank(projectName)) {
			projectName = SuiteConfiguration.suiteName;
		}
		buildNumber = returnCalcValue("buildNumber", element);
	}

	private String returnCalcValue(String name, Element element) {
		String p_name = System.getProperty(name);
		if (p_name != null && !StringUtils.isBlank(p_name))
			return p_name;
		else {
			p_name = element.getAttribute(name);
			if (p_name != null && !StringUtils.isBlank(p_name)) {
				return p_name;
			}
			return null;
		}
	}

	public String getProjectName() {
		return projectName;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	/***
	 * Return {@link IMappingData}
	 * @param method
	 * @return
	 */
	public IMappingData getMethodData(Method method) {

		List<String> browserXml = new ArrayList<String>();
		String testData = new String();
		String runStartegy = new String();
		String className;
		String PackageName;
		String methodName = method.getName();
		String fullMethodName = method.getDeclaringClass().getName() + "."
				+ methodName;
		className = method.getDeclaringClass().getName();
		PackageName = method.getDeclaringClass().getPackage().getName();
		if (packageBrowserXml.get(PackageName) != null
				&& !packageBrowserXml.get(PackageName).isEmpty()) {
			browserXml = packageBrowserXml.get(PackageName);
		}
		if (classBrowserXml.get(className) != null
				&& !classBrowserXml.get(className).isEmpty()) {

			browserXml = classBrowserXml.get(className);
		}
		if (methodBrowserXml.get(fullMethodName) != null
				&& !methodBrowserXml.get(fullMethodName).isEmpty()) {
			browserXml = methodBrowserXml.get(fullMethodName);
		}

		if (browserXml.isEmpty()) {
			throw new FrameworkException(
					"Oops we are unable to find clientEnvironment for method with name:"
							+ methodName
							+ " ,Moreover there is no mapping of clientEnvironment in Mapping.xml for Package:"
							+ PackageName
							+ " and Class Name:"
							+ className
							+ " ,Either put clientEnvironment attribute in package tag,Class tag,methodName tag of this method");

		}
		// test data update
		if (packageDataXml.get(PackageName) != null
				&& !packageDataXml.get(PackageName).isEmpty()) {
			testData = packageDataXml.get(PackageName);
		}
		if (classDataXml.get(className) != null
				&& !classDataXml.get(className).isEmpty()) {

			testData = classDataXml.get(className);
		}
		if (methodDataXml.get(fullMethodName) != null
				&& !methodDataXml.get(fullMethodName).isEmpty()) {
			testData = methodDataXml.get(fullMethodName);
		}
		if (testData.isEmpty()) {
			throw new FrameworkException(
					"Oops we are unable to find testData for method with name:"
							+ methodName
							+ " ,Moreover there is no mapping of testData in Mapping.xml for Package:"
							+ PackageName
							+ " and Class Name:"
							+ className
							+ " ,Either put testData attribute in package tag,Class tag,methodName tag of this method");
		}

		// run startegy update
		
		if (packageRunStartegy.get(PackageName) != null
				&& !packageRunStartegy.get(PackageName).isEmpty()) {
			runStartegy = packageRunStartegy.get(PackageName);
		}
		if (classRunStartegy.get(className) != null
				&& !classRunStartegy.get(className).isEmpty()) {

			runStartegy = classRunStartegy.get(className);
		}
		if (methodRunStartegy.get(fullMethodName) != null
				&& !methodRunStartegy.get(fullMethodName).isEmpty()) {
			runStartegy = methodRunStartegy.get(fullMethodName);
		}

		if (runStartegy.isEmpty()) {
			runStartegy = "Full";
		}

		// prepare IMethodData
		return new ImplementIMap.Builder().withClientEnvironment(browserXml)
				.withRunStartegy(runStartegy).withTestData(testData).build();
	}

}
