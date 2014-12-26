package com.springer.omelet.data;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.IRetryAnalyzer;

import com.springer.omelet.data.DataProvider.mapStrategy;
import com.springer.omelet.data.driverconf.IBrowserConf;

public interface IMethodContext {
	
	public List<IProperty> getMethodTestData();
	public List<IBrowserConf> getBrowserConf();
	public mapStrategy getRunStrategy();
	public DataSource getDataProvider();
	public String getAfterMethod();
	public String getBeforeMethod();
	public IRetryAnalyzer getRetryAnalyzer();
	public void updateTestData(Method testMethod);
}
