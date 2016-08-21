package com.lemall.settlement.poi.reflex;

import java.lang.reflect.Method;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.lemall.settlement.poi.exception.ExcelHandleException;
import com.lemall.settlement.poi.reflex.model.MethodInfo;
/**
 * 
 * @author heyongcheng
 *
 */
public class ReflexUtils {
	
	private static final ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
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
	
	/**
	 * 获取反射方法参数
	 * @param method
	 * @return
	 */
	public static MethodInfo getMethodInfo(Method method){
		String[] parameterNames = discoverer.getParameterNames(method);
		Class<?>[] parameterTypes = method.getParameterTypes();
		
		
		return null;
	}
}
