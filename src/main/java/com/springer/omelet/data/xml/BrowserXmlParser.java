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
package com.springer.omelet.data.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.springer.omelet.common.Utils;
import com.springer.omelet.data.BrowserConfiguration;
import com.springer.omelet.data.BrowserConstant;
import com.springer.omelet.data.IBrowserConf;

/***
 * Return list of {@link IBrowserConf} given name of Xml file
 * 
 * @author kapilA
 * 
 */

public class BrowserXmlParser {
	private final DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
	private DocumentBuilder builder = null;
	private Document document = null;
	private List<String> browserXmls;
	private static Map<String, List<HashMap<String, String>>> singleXmlBrowserMap = Collections
			.synchronizedMap(new HashMap<String, List<HashMap<String, String>>>());
	private static final Logger LOGGER = Logger
			.getLogger(BrowserXmlParser.class);

	public BrowserXmlParser(List<String> browserXmlNames) {
		this.browserXmls = browserXmlNames;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOGGER.error(e);
		}
	}

	private List<HashMap<String, String>> readXml(String xmlN) {

		List<HashMap<String, String>> singleXMlList = new ArrayList<HashMap<String, String>>();
		try {
			document = builder.parse(Utils.getResources(this, xmlN));
		} catch (SAXException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}

		Element rootElement = document.getDocumentElement();
		NodeList nodes = rootElement.getChildNodes();

		// How many Client environments
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Element instanceBConf = (Element) node;
				singleXMlList.add(getKeyValue(instanceBConf));
			}

		}
		// Add entry in static HashMap
		singleXmlBrowserMap.put(xmlN, singleXMlList);
		return singleXMlList;
	}

	/**
	 * Return list of {@link IBrowserConf} prepared from the list of the name of the xml recieved
	 * @return
	 */
	public List<IBrowserConf> getBrowserConf() {

		List<HashMap<String, String>> totalList = new ArrayList<HashMap<String, String>>();

		// get the browser xml names compare them in HashMap
		for (String browserXml : browserXmls) {
			if (singleXmlBrowserMap.containsKey(browserXml)) {
				totalList.addAll(singleXmlBrowserMap.get(browserXml));
			} else {
				totalList.addAll(readXml(browserXml));
			}
		}
		// if present then combine them into Single List and return
		List<IBrowserConf> ibrowserList = new ArrayList<IBrowserConf>();
		// Check if for xml there is Already a mapping in the
		// Iterate over the complete list
		for (HashMap<String, String> b_data : totalList) {
			ibrowserList.add(new BrowserConfiguration(b_data));
		}
		return ibrowserList;
	}

	/**
	 * Return key values pair for the the tag ClientEnvironment of the Browser Xml
	 * @param keyElement
	 * @return
	 */
	private HashMap<String, String> getKeyValue(Element keyElement) {

		HashMap<String, String> browserData = new HashMap<String, String>();
		Element element = keyElement;
		for (BrowserConstant b : BrowserConstant.values()) {
			browserData.put(b.toString(), element.getAttribute(b.toString()));
		}
		return browserData;
	}

}
