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
package com.springer.omelet.test.data;

public class XmlApplicationDataTest {

	public void withoutEnvironment_Constructor() {
		// Create object without specifying enviromment
		// load xml file having single Data tag and environment
		// Assert getAppData list with count to one
		// Check some value pageName_key values

	}

	public void withoutEnvironment_mutipleDataSets_withDifferentEnv_in_Data_xml() {
		// load xml having two data sets one having env=live other having
		// env=stage
		// Assert getApp Data =>should have 2 count
		// Some of the values can be asserted by getting IProperty from list and
		// then asserting
	}

	public void withEnvironment_Constructor() {
		// load xml file having single Data tag and environment
		// Assert getAppData list with count to one
		// Check some value pageName_key values
	}

	public void withEnvironment_Constructor_xml_with_different_data_env() {
		// load xml having multiple data sets and different env like staging etc
		// Check if env is provided in the constructor then only those data
		// objects will be fetched
	}

}
