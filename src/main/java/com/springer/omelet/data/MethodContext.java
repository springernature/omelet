package com.springer.omelet.data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.log4testng.Logger;

import com.springer.omelet.data.DataSource;
import com.springer.omelet.data.DataProvider.mapStrategy;
import com.springer.omelet.data.driverconf.IBrowserConf;
import com.springer.omelet.data.driverconf.PrepareDriverConf;
import com.springer.omelet.data.googlesheet.GoogleSheetConstant;
import com.springer.omelet.data.googlesheet.ReadGoogle;
import com.springer.omelet.data.xml.BrowserXmlParser;
import com.springer.omelet.data.xml.MappingParserRevisit;
import com.springer.omelet.data.xml.XmlApplicationData;
import com.springer.omelet.exception.FrameworkException;
import com.springer.omelet.testng.support.RetryAnalyzer;

public class MethodContext implements IMethodContext {
		
	private String methodName;
	private List<IProperty> property;
	private List<IBrowserConf> browserConfig;
	private mapStrategy runStrategy;
	private IRetryAnalyzer retryAnalyzer;
	private String beforeMethod;
	private String afterMethod;
	private DataSource dataSource;
	private Logger LOGGER = Logger.getLogger(MethodContext.class);
	private boolean isDataSourceCalculated = false;
	
	public MethodContext(String methodName){
		this.methodName = methodName;
	} 

	public void setRetryAnalyser(ITestAnnotation methodAnnotation)
	{
		if(methodAnnotation.getRetryAnalyzer() == null)
		{
			methodAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
			retryAnalyzer = methodAnnotation.getRetryAnalyzer();
		}
		else
		{
			retryAnalyzer = methodAnnotation.getRetryAnalyzer();	
		}	
		LOGGER.debug("Setting Retry Analyzer to " + methodAnnotation.getRetryAnalyzer() + " for Method: "
				+ methodName);
	}

	
	public void setDataProvider(ITestAnnotation methodAnnotation, Method testMethod)
	{
		if(testMethod.getGenericParameterTypes().length == 2 &&
				   testMethod.getGenericParameterTypes()[0].equals(IBrowserConf.class) && 
				   testMethod.getGenericParameterTypes()[1].equals(IProperty.class))
		{
			verifyDataProviderName(methodAnnotation, testMethod);
			verifyDataProviderClass(methodAnnotation, testMethod);
		}
		else if(testMethod.getGenericParameterTypes().length == 0)
		{
			 dataSource = DataSource.NoSource;
		}
	}
	
	public void setBeforeAfterMethod(Method testMethod)
	{
		if (testMethod != null) {
			if (testMethod
					.getAnnotation(org.testng.annotations.BeforeMethod.class) != null) {
				beforeMethod = testMethod.getDeclaringClass().getName();
			}
			if (testMethod
					.getAnnotation(org.testng.annotations.AfterMethod.class) != null) {
				afterMethod = testMethod.getDeclaringClass().getName();
			}
		}
	}
	
	/*
	 * TO DO: This one is inefficient, we have to parse the
	 * config file for every method. Try to move it
	 * in the above layer, so that parsing happens only once.
	 */
	private DataSource getDataSourceParameter()
	{
		if(!isDataSourceCalculated)
		{
			Map<String, String> tempMap = new HashMap<String, String>();
			PrepareDriverConf configuration = new PrepareDriverConf(tempMap);
			try
			{
				dataSource = DataSource.valueOf(configuration.refineBrowserValues().checkForRules().get().getDataSource());
			}
			catch(Exception exp) 
			{
				dataSource = DataSource.Invalid;
			}
			isDataSourceCalculated = true;
		}
		return dataSource;
	}

	@Override
	public List<IProperty> getMethodTestData()
	{
		return property;
	}
	
	@Override
	public List<IBrowserConf> getBrowserConf()
	{
		return browserConfig;
	}
	
	@Override
	public mapStrategy getRunStrategy()
	{
		return runStrategy;
	}
	
	@Override
	public DataSource getDataProvider()
	{
		return dataSource;
	}
	
	public IRetryAnalyzer getRetryAnalyzer()
	{
		return retryAnalyzer;
	}
	
	public void updateTestData(Method testMethod)
	{
		DataSource dataProviderName = getDataSourceParameter();
		String evironment = System.getProperty("env-type");
		
		if(dataProviderName.equals(DataSource.GoogleData))
		{
			updateGoogleSheet(testMethod, evironment);
		}
		else if(dataProviderName.equals(DataSource.XmlData))
		{
			updateXml(testMethod, evironment);
		}
	}

    private void verifyDataProviderName(ITestAnnotation testAnnotation,
            Method testMethod) {
        if (StringUtils.isNotBlank(testAnnotation.getDataProvider())) {
            if (!(testAnnotation.getDataProvider().equalsIgnoreCase(DataSource.GoogleData.toString()) || testAnnotation
                    .getDataProvider().equalsIgnoreCase(DataSource.XmlData.toString()))) {
                throw new FrameworkException(
                        "Please fix the DataProvider name for data provider in method:"
                                + testMethod.getName() + " which should be"
                                + " either XmlData or GoogleData");
            }
        } else {
            testAnnotation.setDataProvider(getDataSourceParameter().name());
            dataSource = getDataSourceParameter();
            LOGGER.debug("Setting Data provider for method: "
                    + testMethod.getName() + " value: "
                    + testAnnotation.getDataProvider());
        }
    }

    private void verifyDataProviderClass(ITestAnnotation testAnnotation,
            Method testMethod) {
        if (StringUtils.isNotBlank(testAnnotation.getDataProviderClass()
                .toString())) {
            if (!testAnnotation.getDataProviderClass().equals(
                    com.springer.omelet.data.DataProvider.class)) {
                throw new FrameworkException(
                        "please fix data provider class for method:"
                                + testMethod.getName());
            }
        } else {
            testAnnotation
            .setDataProviderClass(com.springer.omelet.data.DataProvider.class);
            LOGGER.debug("Setting Data provider class for method: "
            + testMethod.getName() + " value "
            + testAnnotation.getDataProviderClass().getName());
        }
    }
    
	private void checkGoogleUserNameAndPassword(String methodName) {
		if (StringUtils.isBlank(System
				.getProperty(GoogleSheetConstant.GOOGLEUSERNAME))
				&& StringUtils.isBlank(System
						.getProperty(GoogleSheetConstant.GOOGLEPASSWD))
				&& StringUtils.isBlank(System
						.getProperty(GoogleSheetConstant.GOOGLESHEETNAME))) {
			throw new FrameworkException(
					"Method with name:"
							+ methodName
							+ "required Google Sheet as Test Data , please provide arguments -DgoogleUsername and -DgoogelPassword");
		}
	}
    
	private void updateGoogleSheet(Method method, String environment) {
		String methodName = method.getDeclaringClass().getName() + "."
				+ method.getName();
		checkGoogleUserNameAndPassword(methodName);
		// System.out.println(System.getProperty(googleUsername));
		ReadGoogle readGoogle = new ReadGoogle(
				System.getProperty(GoogleSheetConstant.GOOGLEUSERNAME),
				System.getProperty(GoogleSheetConstant.GOOGLEPASSWD),
				System.getProperty(GoogleSheetConstant.GOOGLESHEETNAME));
		RefineMappedData refinedData = new RefineMappedData(readGoogle);
		IMappingData mapData = refinedData.getMethodData(method);
		browserConfig =  readGoogle.getBrowserListForSheet(mapData);
		property = readGoogle.getMethodData(environment, mapData);
        runStrategy = mapData.getRunStartegy();

	}
	
	private void updateXml(Method method, String environment) {
		String methodName = method.getDeclaringClass().getName() + "."
				+ method.getName();
		MappingParserRevisit mpr = new MappingParserRevisit("Mapping.xml");
		RefineMappedData refinedMappedData = new RefineMappedData(mpr);
		IMappingData mapD = refinedMappedData.getMethodData(method);
		XmlApplicationData xmlapData = null;
		if (environment != null && !StringUtils.isBlank(environment)) {
			// get the xml name from MappingParser Static Method
			xmlapData = new XmlApplicationData(mapD.getTestData(), environment);
			property =  xmlapData.getAppData();
		} else {
			xmlapData = new XmlApplicationData(mapD.getTestData());
			property = xmlapData.getAppData();
		}
		BrowserXmlParser bxp = new BrowserXmlParser(mapD.getClientEnvironment());		
		browserConfig =  bxp.getBrowserConf();
        runStrategy = mapD.getRunStartegy();
	}

	@Override
	public String getAfterMethod() {
		// TODO Auto-generated method stub
		return beforeMethod;
	}

	@Override
	public String getBeforeMethod() {
		// TODO Auto-generated method stub
		return afterMethod;
	}

}
