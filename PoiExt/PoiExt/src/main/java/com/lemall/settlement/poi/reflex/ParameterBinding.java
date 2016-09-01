package com.lemall.settlement.poi.reflex;

import com.lemall.settlement.poi.reflex.model.MethodInfo;
import com.lemall.settlement.poi.web.WebRequest;

public class ParameterBinding {
	
	/**
	 * 绑定方法参数
	 * @param methodInfo
	 * @param object
	 */
	public static Object[] getBindingParameters(MethodInfo methodInfo,WebRequest request){
		
		if(methodInfo.getParamNames() == null || methodInfo.getParamTypes().length == 0){
			return null;
		}
		Object[] args = new Object[methodInfo.getParamTypes().length];
		
		for(int i=0;i<methodInfo.getParamTypes().length;i++){
			
			args[i] = resolveProvidedArgument(methodInfo.getParamNames()[i],methodInfo.getParamTypes()[i],request);
			
		}
		
		return args;
	}
	
	/**
	 * 参数绑定
	 * @param name
	 * @param clazz
	 * @param providedArgs
	 * @return
	 */
	private static Object resolveProvidedArgument(String name, Class<?> clazz, WebRequest request) {
		return null;
	}
}
