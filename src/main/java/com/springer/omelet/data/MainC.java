package com.springer.omelet.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gdata.util.ServiceException;
import com.springer.omelet.data.googlesheet.ReadGoogle;

public class MainC {

	public static void main(String[] args) throws IOException, ServiceException {
		// TODO Auto-generated method stub
		/*MappingParserRevisit mapP = new MappingParserRevisit("/home/kapil/git/sprcom-ui-tests/src/test/resources/Mapping.xml");
		System.out.println("Name of the class is:"+mapP.getClass().getPackage().getName());
		String test = "com.springer.testData.hello";
		String test1 = "com.springer";
		String test2 = "comSpringerHello";
		String test3 = "com.123";
		// System.out.println(test.split("\\.",-2)[0]);
		System.out.println(stripLastStringAfterDot(test));
		System.out.println(stripLastStringAfterDot(test2));
		System.out.println(stripLastStringAfterDot(test1));
		System.out.println(stripLastStringAfterDot(test3));
		Map<String, IMappingData> testData = mapP.getPrimaryData();
		for (String key : testData.keySet()) {
			System.out.println("Key:" + key + " TestData:"
					+ testData.get(key).getTestData());
			System.out.println("ClientEnvironment:"
					+ testData.get(key).getClientEnvironment());
			System.out.println("Run Strategy:"
					+ testData.get(key).getRunStartegy());
		}*/
		ReadGoogle readGoogle = new ReadGoogle("kapuser321@gmail.com", "user123456", "omelet");
		Map<String,IMappingData> mapD = readGoogle.getPrimaryData();
		for(String key:mapD.keySet()){
			System.out.println("Key is:"+key+"Value is:"+mapD.get(key).getTestData());
			System.out.println("Key is:"+key+"Value is:"+mapD.get(key).getClientEnvironment().size());
		}
		//Map<String,List<IBrowserConf>> bsList = readGoogle.getBrowserListForSheet(mapD);
		//Map<String,List<IProperty>> bsData = readGoogle.getMethodsData("live", readGoogle);
		/*for(String sheetName:bsList.keySet()){
			for(IBrowserConf browserConf:bsList.get(sheetName)){
				System.out.println("Key is:"+sheetName+browserConf.getRemoteURL());
				
			}
		}
		for(String method:bsData.keySet()){
			for(IProperty prop:bsData.get(method)){
				//iterate through map and display key value
//				System.out.println(prop.getValue("Key1"));
			}
		}*/

	}

	public static String stripLastStringAfterDot(String text) {
		String[] splitString = text.split("\\.");
		if (splitString.length > 1) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < splitString.length - 1; i++) {
				sb.append(splitString[i]);
				sb.append(".");
			}
			return StringUtils.chop(sb.toString());
		}
		return text;
	}

}
