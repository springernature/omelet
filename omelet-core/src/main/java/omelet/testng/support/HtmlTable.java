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
package omelet.testng.support;

import java.util.Map;

import omelet.driver.DriverManager;

import org.testng.asserts.IAssert;

/***
 * For creating and updating HTML Table in Reports
 * 
 * @author kapilA
 * 
 */
public class HtmlTable {
	private boolean screenShotFlag = DriverManager.getBrowserConf().isScreenShotFlag();
	private StringBuilder sb = new StringBuilder();
	private Map<IAssert, String> assertMap;
	private String testCaseName;

	public HtmlTable(Map<IAssert, String> assertMap, String testCaseName) {
		this.assertMap = assertMap;
		this.testCaseName = testCaseName;
	}

	/***
	 * Return String of the data having Table and all the rows data appended to
	 * it Can be consumed in Reporter.log()
	 * 
	 * @return
	 */
	public String getTable() {
		sb.append("<style type=\"text/css\">table{border-collapse: collapse;border: 1px solid black;color: #008000} table td{ border: 1px solid black;} table tr{ border: 1px solid black;}table th{border: 1px solid black;font-size:15px; font-weight: bold;color: #54B948;;background-color:#2F4F4F}</style>");
		sb.append("<table >" + "<tr>" + "<th colspan='5'>" + testCaseName + "</th>"
				+ "</tr>" + "<tr>" + "<th>Step No</th>"
				+ "<th>Description</th>" + "<th>Status</th>"
				+ "<th>Expected</th>" + "<th>Actual</th>");
		if (screenShotFlag) {
			sb.append("<th>ScreenShot</th>");
		}
		sb.append("</tr>");
		int i = 0;
		for (Map.Entry<IAssert, String> assertM : assertMap.entrySet()) {
			boolean result = assertM.getKey().getExpected()
					.equals(assertM.getKey().getActual());
			String printResult = (result) ? "Pass" : "Fail";
			i++;
			sb.append("<tr>" + "<td>" + i + "</td>" + "<td>"
					+ assertM.getKey().getMessage() + "</td>");
			if (result) {
				sb.append("<td style=\"color: #000080\">" + printResult
						+ "</td>");
			} else {
				sb.append("<td style=\"color: #FF0000\">" + printResult
						+ "</td>");
			}
			sb.append("<td>" + assertM.getKey().getExpected() + "</td>"
					+ "<td>" + assertM.getKey().getActual() + "</td>");
			if (!result && screenShotFlag) {
				sb.append("<td>" + "<a href='" + assertM.getValue()
						+ "' target='_blank'>screenShotLink</a>" + "</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
}
