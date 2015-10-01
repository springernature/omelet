package omelet.testng.support;

import omelet.data.DataProvider;
import omelet.data.IMethodContext;

public class MethodContextCollection {
	
	public static IMethodContext getMethodContext(String methodName){
		return RetryIAnnotationTransformer.methodContextHolder.get(methodName);
	}

}
