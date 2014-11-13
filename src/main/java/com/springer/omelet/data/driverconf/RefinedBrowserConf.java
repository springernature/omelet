package com.springer.omelet.data.driverconf;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.springer.omelet.common.Utils;
import com.springer.omelet.data.PropertyValueMin;

/**
 * Refine Browser data based on the Hierarchy
 * default-->Framework-->ClientEnv-->CommandLine
 * 
 * @author kapil
 * 
 */
public class RefinedBrowserConf {

	private Map<String, String> clientBrowserData;
	private boolean isFrameworkProperties;
	private PropertyValueMin frameworkPropData = null;

	public RefinedBrowserConf(Map<String, String> clientBrowserData,
			String standBylookUpFileName) {
		this.clientBrowserData = clientBrowserData;
		setFrameworkProperties(standBylookUpFileName);

	}

	private void setFrameworkProperties(String standBylookUpFileName) {
		String pathOfFile = Utils.getResources(RefinedBrowserConf.class,
				standBylookUpFileName);
		if (null == pathOfFile) {
			isFrameworkProperties = false;
		} else {
			isFrameworkProperties = true;
			frameworkPropData = new PropertyValueMin(Utils.getResources(this,
					standBylookUpFileName));
		}
	}

	public String get(String key, String defaultValue) {
		String refinedValue = defaultValue;
		refinedValue = getFromFrameworkProp(key, refinedValue);
		refinedValue = getFromClientEnv(key, refinedValue);
		refinedValue = getFromJvmArgs(key, refinedValue);
		return refinedValue;
	}

	private String getFromFrameworkProp(String key, String value) {
		if (isFrameworkProperties) {
			String tempValue = frameworkPropData.getValue(key);
			if (isNotBlank(tempValue)) {
				return tempValue;
			}
		}
		return value;
	}

	private String getFromClientEnv(String key, String value) {
		String tempValue = clientBrowserData.get(key);
		if (isNotBlank(tempValue)) {
			return tempValue;
		}
		return value;
	}

	private boolean isNotBlank(String value) {
		return StringUtils.isNotBlank(value);
	}

	private String getFromJvmArgs(String key, String value) {
		String tempValue = System.getProperty(key);
		return isNotBlank(tempValue) ? tempValue : value;
	}

}
