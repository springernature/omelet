package com.springer.omelet.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.log4testng.Logger;

import com.springer.omelet.data.DataProvider.mapStrategy;
import com.springer.omelet.data.driverconf.IBrowserConf;
import com.springer.omelet.data.driverconf.PrepareDriverConf;
import com.springer.omelet.exception.FrameworkException;
import com.springer.omelet.testng.support.RetryAnalyzer;

public class MethodContext implements IMethodContext {

	private String methodName;
	private Method method;
	private List<IProperty> testData;
	private List<IBrowserConf> browserConfig;
	private mapStrategy runStrategy;
	private IRetryAnalyzer retryAnalyzer;
	private boolean beforeMethod;
	private boolean afterMethod;
	private DataSource dataSource;
	private Logger LOGGER = Logger.getLogger(MethodContext.class);
	private boolean isDataSourceCalculated = false;

	public MethodContext(Method method) {
		this.method = method;
		setBeforeAfterMethod();
	}

	public void setRetryAnalyser(ITestAnnotation methodAnnotation) {
		if (methodAnnotation.getRetryAnalyzer() == null) {
			methodAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
			retryAnalyzer = methodAnnotation.getRetryAnalyzer();
		} else {
			retryAnalyzer = methodAnnotation.getRetryAnalyzer();
		}
		LOGGER.debug("Setting Retry Analyzer to "
				+ methodAnnotation.getRetryAnalyzer() + " for Method: "
				+ methodName);
	}

	public void setBrowserConf(List<IBrowserConf> browserConfs) {
		this.browserConfig = browserConfs;
	}

	public void setTestData(List<IProperty> testDataL) {
		this.testData = testDataL;
	}

	public void setRunStrategy(mapStrategy runStrategy) {
		this.runStrategy = runStrategy;
	}

	public void setDataProvider(ITestAnnotation methodAnnotation,
			Method testMethod) {
		if (testMethod.getGenericParameterTypes().length == 2
				&& testMethod.getGenericParameterTypes()[0]
						.equals(IBrowserConf.class)
				&& testMethod.getGenericParameterTypes()[1]
						.equals(IProperty.class)) {
			verify_UpdateDataProviderName(methodAnnotation, testMethod);
			verify_UpdateDataProviderClass(methodAnnotation, testMethod);
		} else if (testMethod.getGenericParameterTypes().length == 0) {
			dataSource = DataSource.NoSource;
		}
	}

	private void setBeforeAfterMethod() {
		beforeMethod = checkAnnotation(method.getDeclaringClass(), org.testng.annotations.BeforeMethod.class);
		afterMethod = checkAnnotation(method.getDeclaringClass(), org.testng.annotations.AfterMethod.class);
	}

	private <T extends Annotation> boolean checkAnnotation(Class<?> classToCheck,Class<T> annotationToVerify) {
		if (!classToCheck.getName().contains("java.lang.Object")) {
			for (Method method : classToCheck.getMethods()) {
				if (method.getAnnotation(annotationToVerify) != null) {
					return true;
				}
			}
			return checkAnnotation(classToCheck.getSuperclass(),annotationToVerify);
		}
		return false;
	}

	/*
	 * TO DO: This one is inefficient, we have to parse the config file for
	 * every method. Try to move it in the above layer, so that parsing happens
	 * only once.
	 */
	private DataSource getDataSourceParameter() {
		if (!isDataSourceCalculated) {
			Map<String, String> tempMap = new HashMap<String, String>();
			PrepareDriverConf configuration = new PrepareDriverConf(tempMap);
			try {
				// no need for CheckforRules as it only adds confusion
				dataSource = DataSource.valueOf(configuration
						.refineBrowserValues().checkForRules().get()
						.getDataSource());
			} catch (Exception exp) {
				throw new FrameworkException(
						"it seems there is no DatSource provided for the testMethod:"
								+ methodName);
				// dataSource = DataSource.Invalid;
			}
			isDataSourceCalculated = true;
		}
		return dataSource;
	}

	@Override
	public List<IProperty> getMethodTestData() {
		return this.testData;
	}

	@Override
	public List<IBrowserConf> getBrowserConf() {
		return browserConfig;
	}

	@Override
	public mapStrategy getRunStrategy() {
		return runStrategy;
	}

	@Override
	public DataSource getDataProvider() {
		return dataSource;
	}

	public IRetryAnalyzer getRetryAnalyzer() {
		return retryAnalyzer;
	}

	/**
	 * update and verify dataProvider name for method which are already having
	 * dataProviderName
	 * 
	 * @param dataProperty
	 */

	private void setDataSourceForNonBlankMethod(String dataProperty) {
		if (dataProperty.equalsIgnoreCase(DataSource.GoogleData.toString())) {
			dataSource = DataSource.GoogleData;
		} else if (dataProperty
				.equalsIgnoreCase((DataSource.XmlData.toString()))) {
			dataSource = DataSource.XmlData;
		} else {
			throw new FrameworkException(
					"Please fix the DataProvider name for data provider in method:"
							+ methodName + " which should be"
							+ " either XmlData or GoogleData");
		}
	}

	private void verify_UpdateDataProviderName(ITestAnnotation testAnnotation,
			Method testMethod) {

		if (StringUtils.isNotBlank(testAnnotation.getDataProvider())) {
			setDataSourceForNonBlankMethod(testAnnotation.getDataProvider());
		} else {

			testAnnotation.setDataProvider(getDataSourceParameter().name());
			dataSource = getDataSourceParameter();
			LOGGER.debug("Setting Data provider for method: "
					+ testMethod.getName() + " value: "
					+ testAnnotation.getDataProvider());
		}
	}

	private void verify_UpdateDataProviderClass(ITestAnnotation testAnnotation,
			Method testMethod) {

		if (testAnnotation.getDataProviderClass() != null
				&& StringUtils.isNotBlank(testAnnotation.getDataProviderClass()
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

	/*
	 * private void checkGoogleUserNameAndPassword(String methodName) { if
	 * (StringUtils.isBlank(System
	 * .getProperty(GoogleSheetConstant.GOOGLEUSERNAME)) &&
	 * StringUtils.isBlank(System
	 * .getProperty(GoogleSheetConstant.GOOGLEPASSWD)) &&
	 * StringUtils.isBlank(System
	 * .getProperty(GoogleSheetConstant.GOOGLESHEETNAME))) { throw new
	 * FrameworkException( "Method with name:" + methodName +
	 * "required Google Sheet as Test Data , please provide arguments -DgoogleUsername and -DgoogelPassword"
	 * ); } }
	 * 
	 * private void updateGoogleSheet(Method method, String environment) {
	 * String methodName = method.getDeclaringClass().getName() + "." +
	 * method.getName(); checkGoogleUserNameAndPassword(methodName); //
	 * System.out.println(System.getProperty(googleUsername)); ReadGoogle
	 * readGoogle = new ReadGoogle(
	 * System.getProperty(GoogleSheetConstant.GOOGLEUSERNAME),
	 * System.getProperty(GoogleSheetConstant.GOOGLEPASSWD),
	 * System.getProperty(GoogleSheetConstant.GOOGLESHEETNAME));
	 * RefineMappedData refinedData = new RefineMappedData(readGoogle);
	 * IMappingData mapData = refinedData.getMethodData(method); browserConfig =
	 * readGoogle.getBrowserListForSheet(mapData); property =
	 * readGoogle.getMethodData(environment, mapData); runStrategy =
	 * mapData.getRunStartegy();
	 * 
	 * }
	 * 
	 * private void updateXml(Method method, String environment) { String
	 * methodName = method.getDeclaringClass().getName() + "." +
	 * method.getName(); MappingParserRevisit mpr = new
	 * MappingParserRevisit("Mapping.xml"); RefineMappedData refinedMappedData =
	 * new RefineMappedData(mpr); IMappingData mapD =
	 * refinedMappedData.getMethodData(method); XmlApplicationData xmlapData =
	 * null; if (environment != null && !StringUtils.isBlank(environment)) { //
	 * get the xml name from MappingParser Static Method xmlapData = new
	 * XmlApplicationData(mapD.getTestData(), environment); property =
	 * xmlapData.getAppData(); } else { xmlapData = new
	 * XmlApplicationData(mapD.getTestData()); property =
	 * xmlapData.getAppData(); } BrowserXmlParser bxp = new
	 * BrowserXmlParser(mapD.getClientEnvironment()); browserConfig =
	 * bxp.getBrowserConf(); runStrategy = mapD.getRunStartegy(); }
	 */

	@Override
	public boolean isAfterMethod() {
		// TODO Auto-generated method stub
		return afterMethod;
	}

	/*
	 * private void updateAfterMethod(){ Class<?> className =
	 * method.getTestMethod() .getConstructorOrMethod().getDeclaringClass(); }
	 * 
	 * private void updateBeforeMethod(){
	 * 
	 * }
	 */

	@Override
	public boolean isBeforeMethod() {
		// TODO Auto-generated method stub
		return beforeMethod;
	}

}
