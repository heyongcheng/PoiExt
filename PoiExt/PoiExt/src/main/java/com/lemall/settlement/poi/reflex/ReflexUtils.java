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
	
	private static final Map<Method,MethodInfo> methodInfoCache = new ConcurrentHashMap<Method,MethodInfo>();
	
	
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
			if(method == null)
				throw new ExcelHandleException("export processor: " + bean.getClass().getName() + " method:" + methodName + " is not exist");
			/**  返回方法调用   **/
			return method.invoke(bean, ParameterBinding.getBindingParameters(getMethodInfo(method), args));
			
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
		/** method缓存  **/
		MethodInfo methodInfo = methodInfoCache.get(method);
		if(methodInfo != null)
			return methodInfo;
		/**
		 * 获取method信息
		 */
		methodInfo = new MethodInfo();
		methodInfo.setMethod(method);
		methodInfo.setParamNames(discoverer.getParameterNames(method));
		methodInfo.setParamTypes(method.getParameterTypes());
		/**  加入缓存   **/
		methodInfoCache.put(method, methodInfo);
		return methodInfo;
	}
}
