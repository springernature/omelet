package com.springer.omelet.data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.springer.omelet.data.DataProvider.mapStrategy;
import com.springer.omelet.exception.FrameworkException;

public class RefineMappedData {

	private Map<String, IMappingData> primaryDataMap;

	public RefineMappedData(IDataSource dataSource) {
		primaryDataMap = dataSource.getPrimaryData();
	}

	/**
	 * Get refined methodData based on the Hierarchy 
	 * if attributes "testData","ClientStrategy","RunStrategy" found in method else in class else in package 
	 * else exception
	 * @param methodName
	 * @return
	 */
	public IMappingData getMethodData(Method methodName) {
		System.out.println(getRefinedClientEnvironment(methodName).get(0));
		
		return new ImplementIMap.Builder()
				.withTestData(getRefinedTestData(methodName))
				.withClientEnvironment(getRefinedClientEnvironment(methodName))
				.withRunStartegy(getRunStrategy(methodName).toString()).build();
	}

	private String getRefinedTestData(Method method) {
		IMappingData methodVal = primaryDataMap.get(method.getName().toString());
		IMappingData classVal = primaryDataMap.get(
				method.getDeclaringClass().getName().toString());
		IMappingData packageVal = primaryDataMap.get(
				method.getDeclaringClass().getPackage().getName().toString().toString());

		if (methodVal != null && StringUtils.isNotBlank(methodVal.getTestData())) {
			return methodVal.getTestData();
		} else if (classVal != null && StringUtils.isNotBlank(classVal.getTestData())) {
			System.out.println(classVal.getTestData());
			return classVal.getTestData();
		} else if (packageVal != null && StringUtils.isNotBlank(packageVal.getTestData())) {
			System.out.println(packageVal.getTestData());
			return packageVal.getTestData();
		}
		throw new FrameworkException(
				"There is no mapping Defined for the method");
	}

	private List<String> getRefinedClientEnvironment(Method method) {
		IMappingData methodClientData = primaryDataMap.get(
				method.getName().toString());
		IMappingData classClientData = primaryDataMap.get(
				method.getDeclaringClass().getName().toString());
		IMappingData packageClientData = primaryDataMap.get(
				method.getDeclaringClass().getPackage().getName().toString());

		//if 1st entry is list is zero then for sure its fake list of client Environment
		if (methodClientData != null && StringUtils.isNotBlank(methodClientData.getClientEnvironment().get(0))) {
			return methodClientData.getClientEnvironment();
		} else if (classClientData != null && StringUtils.isNotBlank(classClientData.getClientEnvironment().get(0))) {
			return classClientData.getClientEnvironment();
		} else if (packageClientData != null && StringUtils.isNotBlank(packageClientData.getClientEnvironment().get(0))) {
			return packageClientData.getClientEnvironment();
		}
		throw new FrameworkException(
				"There is no mapping Defined for the method");
	}

	private mapStrategy getRunStrategy(Method method) {
		IMappingData methodRunStartegy = primaryDataMap.get(
				method.getName().toString());
		IMappingData classRunStartegy = primaryDataMap.get(
				method.getDeclaringClass().getName().toString());
		IMappingData packageRunStartegy = primaryDataMap.get(
				method.getDeclaringClass().getPackage().getName().toString());

		if (methodRunStartegy != null && methodRunStartegy.getRunStartegy()!= null) {
			return methodRunStartegy.getRunStartegy();
		} else if (classRunStartegy != null && classRunStartegy.getRunStartegy() != null) {
			return classRunStartegy.getRunStartegy();
		} else if (packageRunStartegy != null && packageRunStartegy.getRunStartegy() != null) {
			return packageRunStartegy.getRunStartegy();
		}
		return mapStrategy.Optimal;

	}
}
