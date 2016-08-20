package com.lemall.settlement.poi.utils;

import java.lang.reflect.Method;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.lemall.settlement.poi.exception.ExcelHandleException;

public class ReflexUtils {
	
	/**
	 * 反射调用
	 * @param bean
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object bean, String methodName, Object[] args){
		try {
			Method method = null;
			Method[] methods = bean.getClass().getMethods();
			for(int i=0;i<methods.length;i++){
				if(methods[i].getName().equals(methodName)){
					method = methods[i];
					break;
				}
			}
			
			return method.invoke(bean, args);
		} catch (Exception e) {
			throw new ExcelHandleException("export processor: " + bean.getClass().getName() + " invokeMethod:" + methodName + " error");
		}
	}
	
	public static String[] getMethodParamName(Method method){
		ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
		return discoverer.getParameterNames(method);
	}
}
