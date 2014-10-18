package com.springer.omelet.test.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.springer.omelet.data.IDataSource;
import com.springer.omelet.data.IMappingData;
import com.springer.omelet.data.ImplementIMap;
import com.springer.omelet.data.RefineMappedData;

public class MappedDataTest {
	
/*	//MappedData mapD = new MappedData(dataSource);
	
	Method mTestMethod = Mockito.mock(Method.class);
	IDataSource mDataSource = Mockito.mock(IDataSource.class);
	IMappingData mConfigData = Mockito.mock(IMappingData.class);
	MappedData finalConfig;
	@Test
	public void verifygetValues_MethodValuePresent(){
		String methodName = "com.springer.TestClass.testMethod";
		String className = "com.springer.TestClass";
		String packageName = "com.springer";
		Map<String, IMappingData> testConfig = new HashMap<String, IMappingData>();
		testConfig.put(methodName,new ImplementIMap.Builder()
		.withClientEnvironment(new ArrayList<String>(Arrays.asList("Browser1","Browser2")))
		.withRunStartegy(null)
		.withTestData(null)
		.build());
		//Mockito.when(mDataSource.getPrimaryData()).thenReturn(value)
		finalConfig = new MappedData(mDataSource);
		Mockito.doReturn(new HashMap()).when(test.getPrimaryData());
		IMappingData mockMapData = Mockito.mock(IMappingData.class);
		Mockito.when(mockMapData.getClientEnvironment()).thenReturn(new ArrayList());
		Mockito.when(mockMapData.getRunStartegy()).thenReturn(value)
		MappedData mpa = new MappedData(test);
		mpa.getMethodData(methodName)
	}
	
	public void verifygetValues_OnlyTestDataPresentForMethod_parentPresent(){
		
	}
	
	public void verifyExceptionIfCompleteMethodMappingNotPresent_parentNotPresent(){
		
	}
	
	public void verifyIfMethodMappingNotPresent_ParentPresent(){
		
	}
	
	public void verifyIfMethodMappingNotPresent_ParentNotPresent(){
		
	}
	
	public void verifyRunStrategyDefaultsToOptimalIfNoPresent(){
		
	}

	private class ImplementIData implements IDataSource{

		@Override
		public Map<String, IMappingData> getPrimaryData() {
			// TODO Auto-generated method stub
			IMappingData mapD = Mockito.mock(ImplementIMap.class);
			Mockito.stub(mapD.getClientEnvironment()).toReturn(new ArrayList<String>());
			Map<String, IMappingData> testData = new HashMap<String, IMappingData>();
			testData.put("com.springer.class.method", new ImplementIMap.Builder().withClientEnvironment(clientEData))
		}
		
	}*/
}
