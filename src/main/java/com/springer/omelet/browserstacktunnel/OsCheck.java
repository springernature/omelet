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
package com.springer.omelet.browserstacktunnel;

import org.apache.log4j.Logger;

import com.springer.omelet.exception.FrameworkException;

/***
 * Return current Operating system
 * 
 * @author kapilA
 * 
 */
public class OsCheck {
	
	private final static Logger LOGGER = Logger.getLogger(OsCheck.class);
	private static String OS = System.getProperty("os.name").toLowerCase();
	public static enum OsName {
		WIN, UNIX, MAC
	};

	public static OsName getOS() {
		LOGGER.debug("OS Name is:" + OS);
		if (OS.indexOf("win") >= 0)
			return OsName.WIN;
		else if (OS.indexOf("mac") >= 0)
			return OsName.MAC;
		else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0
				|| OS.indexOf("aix") > 0)
			return OsName.UNIX;
		else
			throw new FrameworkException("Cannot find OS name ,Java returned:"
					+ OS);
	}

}
