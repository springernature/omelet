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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.springer.omelet.data.IProperty;
import com.springer.omelet.data.PropertyMapping;
import com.springer.omelet.exception.FrameworkException;

/***
 * For Parssing Test Data xml return List of {@link IProperty}
 * 
 * @author kapilA
 * 
 */
public class XmlApplicationData {

	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder builder = null;
	private Document document = null;
	private String envType = null;
	private String xmlPath;
	private static final Logger LOGGER = Logger
			.getLogger(XmlApplicationData.class);

	private void initialize() {
		try {

			builder = factory.newDocumentBuilder();
			document = builder.parse(new FileInputStream(Utils.getResources(
					this, xmlPath)));
		} catch (ParserConfigurationException e) {
			LOGGER.error(e);
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
		} catch (SAXException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	public XmlApplicationData(String xmlPath) {
		this.xmlPath = xmlPath;
		initialize();
	}

	public XmlApplicationData(String xmlPath, String envType) {
		this.xmlPath = xmlPath;
		this.envType = envType;
		initialize();

	}

	/***
	 * Return List of DataObjects for All Environment <Data>
	 * 
	 * @param environment
	 * @return
	 */
	private List<Element> getDataObjects() {
		List<Element> dataList = new ArrayList<Element>();
		Element rootElement = document.getDocumentElement();
		NodeList nodes = rootElement.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node instanceof Element) {
				Element data = (Element) node;
				if (envType != null) {
					if (data.getAttribute("environment").equals(envType))
						dataList.add(data);
				} else
					dataList.add(data);

			}
		}

		// Check if Data list is empty throw framework exception as there is no
		// mapping for the xml
		if (dataList.isEmpty() && envType != null) {
			throw new FrameworkException(
					"There is no such environment with name in :" + envType
							+ " in the xml:" + xmlPath);
		}
		return dataList;
	}

	/***
	 * Key value pair data to be used in AUT
	 * 
	 * @return
	 */
	public List<IProperty> getAppData() {
		List<IProperty> dataProperty = new ArrayList<IProperty>();
		HashMap<String, String> keyValue = null;
		for (Element dataObject : getDataObjects()) {
			keyValue = new HashMap<String, String>();
			NodeList pagesOject = dataObject.getElementsByTagName("Pages")
					.item(0).getChildNodes();

			for (int i = 0; i < pagesOject.getLength(); i++) {
				Node pageObject = pagesOject.item(i);
				if (pageObject instanceof Element) {

					Element pageElement = (Element) pageObject;
					String appendText = pageElement.getAttribute("name");
					// GEt all the key value pairs of Page
					NodeList pageData = pageElement.getChildNodes();
					for (int j = 0; j < pageData.getLength(); j++) {
						Node key = pageData.item(j);
						if (key instanceof Element) {
							Element keyElement = (Element) key;
							keyValue.put(
									appendText + "_" + keyElement.getNodeName(),
									keyElement.getTextContent());
						}
					}
				}
			}
			dataProperty.add(new PropertyMapping(keyValue));
		}

		return dataProperty;
	}
}
