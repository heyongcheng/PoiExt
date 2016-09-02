package com.lemall.settlement.poi.reflex;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.lemall.settlement.poi.reflex.model.MethodInfo;
import com.lemall.settlement.poi.web.WebRequest;
/**
 * 解析请求参数
 * @author heyongcheng
 *
 */
public class ArgumentResolver {
	
	/**
	 * 解析参数
	 * @param method
	 * @param request
	 * @return
	 */
	public Object[] resolveArguments(MethodInfo method,WebRequest request){
		
		if(method.getParamTypes() == null || method.getParamTypes().length == 0)
			return null;
		
		Object[] args = new Object[method.getParamTypes().length];
		
		if(request == null){
			return args;
		}
		
		for(int i=0;i<method.getParamTypes().length;i++){
			
			String name = method.getParamNames()[i];
			Class<?> type = method.getParamTypes()[i];
			
			args[i] = resolveWebRequest(type,request);
			if(args[i] != null){
				continue;
			}
			
			if(type == String.class){
				args[i] = resolveStringValue(name,type,request);
				continue;
			}
			
			if(type.isArray()){
				args[i] = resolveArrayValue(name,type,request);
				continue;
			}
			
			if(Collection.class.isAssignableFrom(type)){
				args[i] = resolveCollectionValue(name,type,request);
				continue;
			}
			
			if(Map.class.isAssignableFrom(type)){
				continue;
			}
			
			/**  基本数据类型   **/
			if(isPrimitiveValue(type)){
				if(!StringUtils.isEmpty(request.getParameter(name))){
					args[i] = getPrimitiveValue(request.getParameter(name), type);
				}
				continue;
			}
			
			args[i] = resolveArgs(name,type,request);
		}
		
		return args;
	}
	/**
	 * 集合
	 * @param name
	 * @param type
	 * @param request
	 * @return
	 */
	private Object resolveCollectionValue(String name, Class<?> type, WebRequest request) {
		return null;
	}
	/**
	 * 解析数组参数
	 * @param name
	 * @param type
	 * @param request
	 * @return
	 */
	private Object resolveArrayValue(String name, Class<?> type, WebRequest request) {
		String[] values = request.getParameterValues(name);
		if(values == null || values.length == 0){
			return null;
		}
		Class<?> componentType = type.getComponentType();
		if(componentType == String.class){
			return values;
		}
		Object object = Array.newInstance(componentType, values.length);
		for(int i=0;i<values.length;i++){
			if(!StringUtils.isEmpty(values[i])){
				Array.set(object, i, getPrimitiveValue(values[i], componentType));			
			}
		}
		return object;
	}

	/**
	 * 基本类型参数int, double, float, long, short, boolean, byte, char
	 * @param type
	 * @return
	 */
	private boolean isPrimitiveValue(Class<?> type) {
		boolean primitive = false;
		if(int.class == type){
			primitive = true;
		}
		else if(double.class == type){
			primitive = true;
		}
		else if(float.class == type){
			primitive = true;
		}
		else if(long.class == type){
			primitive = true;
		}
		else if(short.class == type){
			primitive = true;
		}
		else if(boolean.class == type){
			primitive = true;
		}
		else if(byte.class == type){
			primitive = true;
		}
		else if(char.class == type){
			primitive = true;
		}
		/**  包装类   **/
		else if(Integer.class == type){
			primitive = true;
		}
		else if(Double.class == type){
			primitive = true;
		}
		else if(Float.class == type){
			primitive = true;
		}
		else if(Long.class == type){
			primitive = true;
		}
		else if(Short.class == type){
			primitive = true;
		}
		else if(Boolean.class == type){
			primitive = true;
		}
		else if(Byte.class == type){
			primitive = true;
		}
		else if(Character.class == type){
			primitive = true;
		}
		return primitive;
	}
	
	/**
	 * 返回基本数据类型值
	 * @param value
	 * @param type
	 * @return
	 */
	private Object getPrimitiveValue(String value,Class<?> type){
		Object obj = null;
		if(int.class == type){
			obj = Integer.valueOf(value).intValue();				
		}
		else if(double.class == type){
			obj = Double.valueOf(value).doubleValue();
		}
		else if(float.class == type){
			obj = Float.valueOf(value).floatValue();
		}
		else if(long.class == type){
			obj = Long.valueOf(value).longValue();
		}
		else if(short.class == type){
			obj = Short.valueOf(value).shortValue();
		}
		else if(boolean.class == type){
			obj = Boolean.valueOf(value).booleanValue();
		}
		else if(byte.class == type){
			obj = Byte.valueOf(value).byteValue();
		}
		else if(char.class == type){
			char[] charArray = value.toCharArray();
			if(charArray.length == 1){
				obj = charArray[0];
			}else{
				throw new RuntimeException(value + " can not convert to char");				
			}
		}
		/**  包装类   **/
		else if(Integer.class == type){
			obj = Integer.valueOf(value);
		}
		else if(Double.class == type){
			obj = Double.valueOf(value);
		}
		else if(Float.class == type){
			obj = Float.valueOf(value);
		}
		else if(Long.class == type){
			obj = Long.valueOf(value);
		}
		else if(Short.class == type){
			obj = Short.valueOf(value);
		}
		else if(Boolean.class == type){
			obj = Boolean.valueOf(value);
		}
		else if(Byte.class == type){
			obj = Byte.valueOf(value);
		}
		else if(Character.class == type){
			char[] charArray = value.toCharArray();
			if(charArray.length == 1){
				obj = (Character)charArray[0];
			}else{
				throw new RuntimeException(value + " can not convert to Character");				
			}
		}
		return obj;
	}
	
	/**
	 * 解析string参数
	 * @param name
	 * @param type
	 * @param request
	 * @return
	 */
	private Object resolveStringValue(String name, Class<?> type, WebRequest request) {
		
		String value = request.getParameter(name);
		if(!StringUtils.isEmpty(value)){
			return value;
		}
		return null;
	}

	/**
	 * 解析WebRequest参数
	 * @param clazz
	 * @param request
	 * @return
	 */
	private Object resolveWebRequest(Class<?> clazz, WebRequest request) {
		
		if(WebRequest.class.isAssignableFrom(clazz)){
			return request;
		}
		return null;
	}

	/**
	 * 解析参数
	 * @param name
	 * @param type
	 * @param request
	 * @return
	 */
	private Object resolveArgs(String name, Class<?> type, WebRequest request) {
		try {
			Class<?> bean = type.getClass().newInstance();
			
			BeanUtils.populate(bean, request.getParameterMap());
			
			return bean;
		} catch (Exception e) {
			throw new RuntimeException("parameter binding occurred exception.");
		}
	}
	
}
