package com.springer.omelet.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Class for Holding MethodContext per TestMethod
 *
 */
public class MethodContextCollection {
	
	private static MethodContextCollection instance;
	private MethodContextCollection() {}
	private Map<String, MethodContext> contextCollection = Collections.synchronizedMap(new HashMap<String, MethodContext>());

    public static MethodContextCollection getInstance()
    {
	    synchronized(MethodContextCollection.class)
	    {
		    if(instance == null)
		    {
			    instance = new MethodContextCollection();
		    }
	    }
	    return instance;
    }
    
    public void updateMethodContext(String methodName, MethodContext context)
    {
//    	if(contextCollection.containsKey(methodName))
//    	{
//    		contextCollection.replace(methodName, context);  ---> This is not building in travis
//    	}
    	contextCollection.put(methodName, context);    	
    }
    
    public IMethodContext getMethodContext(String methodName)
    {
    	return contextCollection.get(methodName);
    }
}



