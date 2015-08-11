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
package omelet.test.data;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import omelet.data.xml.MappingParserRevisit;
import omelet.driver.SuiteConfiguration;

public class MappingParserTest {
	public static MappingParserRevisit mappingParser;
	public static SuiteConfiguration suiteConfiguration;

	@BeforeMethod
	public void beforeTest() {
		if (System.getProperty("projectName") != null
				&& !System.getProperty("projectName").isEmpty()) {
			System.setProperty("projectName", "");
		}
	}

	@Test
	public void testGetProjectNameFromMappingFile() throws Exception {
		// TODO
		// String projectName = "ProjectNameFromMappingFile";
		// System.setProperty("mappingfile", "MappingWithProjectName.xml");
		// XmlApplicationData
		// Assert.assertEquals(mappingParser.getProjectName(), projectName);
	}

	@Test
	public void testEmptyProjectNameInMappingFile() throws Exception {
		SuiteConfiguration.suiteName = "Suite";
		System.setProperty("mappingfile", "MappingWithoutProjectName.xml");

		Assert.assertEquals(MappingParserRevisit.getProjectName(),
				SuiteConfiguration.suiteName);
	}

	@Test
	public void testProjectNameNotInMappingFile() throws Exception {
		SuiteConfiguration.suiteName = "Suite";
		System.setProperty("mappingfile", "MappingWithEmptyProjectName.xml");
		Assert.assertEquals(MappingParserRevisit.getProjectName(),
				SuiteConfiguration.suiteName);
	}

	@Test
	public void testGetProjectNameFromSystemProperty() throws Exception {
		SuiteConfiguration.suiteName = "Suite";
		String projectName = "ProjectNameFromSystemProperty";
		System.setProperty("projectName", projectName);
		System.setProperty("mappingfile", "MappingWithProjectName.xml");
		Assert.assertEquals(MappingParserRevisit.getProjectName(), projectName);
	}

	@Test
	public void testMappingFileNameNotSet() {
		System.clearProperty("mappingfile");
		Assert.assertEquals(MappingParserRevisit.getMappingFile(),
				"Mapping.xml");
	}

	@Test
	public void testMappingFileNameSetInSystemProperty() {
		System.setProperty("mappingfile", "MappingWithProjectName.xml");
		Assert.assertEquals(MappingParserRevisit.getMappingFile(),
				"MappingWithProjectName.xml");
	}

	// TODO // getMethodData is not called need to add that as well with
	// different scenarios
}
