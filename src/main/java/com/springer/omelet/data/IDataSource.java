package com.springer.omelet.data;

import java.util.Map;

/***
 * Data Sources like xml , Excel , GoogleSheet should implement this interface
 * @author kapil
 *
 */
public interface IDataSource {
	
	public Map<String, IMappingData> getPrimaryData();
}
