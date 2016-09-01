package com.lemall.settlement.poi.reflex;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	
	private static final Map<String,MethodInfo> methodInfoCache = new ConcurrentHashMap<String,MethodInfo>();
	
	
	/**
	 * 反射调用
	 * @param bean
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object bean,Method method, Object ...args){
		try {
			/**  返回方法调用   **/
			return method.invoke(bean, args);
		} catch (Exception e) {
			throw new ExcelHandleException("export processor: " + bean.getClass().getName() + " invokeMethod:" + method.getName() + " error");
		}
	}
	
	/**
	 * 获取反射方法信息
	 * @param bean
	 * @param methodName
	 * @return
	 */
	public static MethodInfo getMethodInfo(Object bean,String methodName){
		
		String methodKey = bean.getClass() + "." + methodName;
		
		MethodInfo methodInfo = methodInfoCache.get(methodKey);
		if(methodInfo != null)
			return methodInfo;
		
		/**  获取方法  **/
		Method method = null;
		Method[] methods = bean.getClass().getMethods();
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().equals(methodName)){
				method = methods[i];
				break;
			}
		}
		if(method == null)
			throw new ExcelHandleException("export processor: " + bean.getClass().getName() + " method:" + methodName + " is not exist");
		/**
		 * 获取method信息
		 */
		methodInfo = new MethodInfo();
		methodInfo.setMethod(method);
		methodInfo.setParamNames(discoverer.getParameterNames(method));
		methodInfo.setParamTypes(method.getParameterTypes());
		/**  加入缓存   **/
		methodInfoCache.put(methodKey, methodInfo);
		
		return methodInfo;
	}
}
