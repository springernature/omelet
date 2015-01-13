package omelet.data;

import java.util.List;

import omelet.data.DataProvider.mapStrategy;
import omelet.data.driverconf.IBrowserConf;

import org.testng.IRetryAnalyzer;

public interface IMethodContext {
	
	public List<IProperty> getMethodTestData();
	public List<IBrowserConf> getBrowserConf();
	public mapStrategy getRunStrategy();
	public DataSource getDataProvider();
	public boolean isAfterMethod();
	public boolean isBeforeMethod();
	public IRetryAnalyzer getRetryAnalyzer();
}
