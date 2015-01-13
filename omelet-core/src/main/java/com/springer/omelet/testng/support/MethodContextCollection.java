package com.springer.omelet.testng.support;

import com.springer.omelet.data.IMethodContext;

public class MethodContextCollection {
	
	public static IMethodContext getMethodContext(String methodName){
		return RetryIAnnotationTransformer.methodContextHolder.get(methodName);
	}

}
