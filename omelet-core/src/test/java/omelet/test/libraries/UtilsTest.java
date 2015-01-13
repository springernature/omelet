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
package omelet.test.libraries;

import java.util.List;

import omelet.common.Utils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UtilsTest {

	public void verifyUniqueName() {

	}

	public void verifyTrimmedUniqueName() {

	}

	@Test
	public void verifyIntegerListFromString() {
		String intText = "This is my first name:1 and surname:2 ";
		List<String> li = Utils.getIntegerListFromString(intText);
		Assert.assertEquals(li.get(0), "1");
		Assert.assertEquals(li.get(1), "2");

	}

}
