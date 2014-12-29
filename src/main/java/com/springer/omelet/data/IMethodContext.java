package com.springer.omelet.data;

import java.util.List;

import org.testng.IRetryAnalyzer;

import com.springer.omelet.data.DataProvider.mapStrategy;
import com.springer.omelet.data.driverconf.IBrowserConf;

public interface IMethodContext {
	
	public List<IProperty> getMethodTestData();
	public List<IBrowserConf> getBrowserConf();
	public mapStrategy getRunStrategy();
	public DataSource getDataProvider();
	public boolean isAfterMethod();
	public boolean isBeforeMethod();
	public IRetryAnalyzer getRetryAnalyzer();
}
