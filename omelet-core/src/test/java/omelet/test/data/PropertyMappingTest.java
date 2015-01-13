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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import omelet.common.Utils;
import omelet.data.PropertyMapping;
import omelet.exception.FrameworkException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PropertyMappingTest {

	@Test
	public void propConstuctor() {
		// Create propertyMapping object and compare the values you get from it
		Properties prop = new Properties();
		InputStream isr = null;
		try {
			isr = new FileInputStream(Utils.getResources(this,
					"PropertyMap_1.properties"));
			prop.load(isr);
			PropertyMapping pm = new PropertyMapping(prop);
			Assert.assertEquals("value1_1", pm.getValue("key1_1"));
			Assert.assertEquals("value1_2", pm.getValue("key1_2"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				isr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Test
	public void singleFilePathConstructor() {
		// Create propertyMapping object and compare the values you get from it
		PropertyMapping pm = new PropertyMapping(Utils.getResources(this,
				"PropertyMap_1.properties"));
		Assert.assertEquals("value1_1", pm.getValue("key1_1"));
		Assert.assertEquals("value1_2", pm.getValue("key1_2"));
	}

	@Test
	public void multipleFilePathConstructor() {
		// Send multiple file path array and check if combined list is recived
		// Extra check can be done by adding same keys in two different
		// properties mapping file
		String[] fileP = {
				Utils.getResources(this, "PropertyMap_1.properties"),
				Utils.getResources(this, "PropertyMap_2.properties") };
		PropertyMapping pm = new PropertyMapping(fileP);
		Assert.assertEquals("value1_1", pm.getValue("key1_1"));
		Assert.assertEquals("value1_2", pm.getValue("key1_2"));

		Assert.assertEquals("value2_1", pm.getValue("key2_1"));
		Assert.assertEquals("value2_2", pm.getValue("key2_2"));
	}

	@Test
	public void hashMapConstructor() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("Key_1_hashM", "Value_1_hashM");
		data.put("Key_2_hashM", "Value_2_hashM");
		data.put("Key_3_hashM", "Value_3_hashM");
		PropertyMapping pm = new PropertyMapping(data);
		Assert.assertEquals(pm.getValue("Key_1_hashM"), "Value_1_hashM");
		Assert.assertEquals(pm.getValue("Key_2_hashM"), "Value_2_hashM");
		Assert.assertEquals(pm.getValue("Key_3_hashM"), "Value_3_hashM");

	}

	@Test(expectedExceptions = FrameworkException.class)
	public void expectException_keyMissing_String() {
		PropertyMapping pm = new PropertyMapping(Utils.getResources(this,
				"PropertyMap_1.properties"));
		Assert.assertEquals("value1_1", pm.getValue("key1_1"));
		Assert.assertEquals("value1_2", pm.getValue("key1_2"));
		// This will throw Framework Exception
		pm.getValue("key1_4");
	}

	private enum test {
		key1_1, key2_5
	}

	@Test(expectedExceptions = FrameworkException.class)
	public void expectException_keyMissing_enum() {
		PropertyMapping pm = new PropertyMapping(Utils.getResources(this,
				"PropertyMap_1.properties"));
		pm.getValue(test.key2_5);
	}

}