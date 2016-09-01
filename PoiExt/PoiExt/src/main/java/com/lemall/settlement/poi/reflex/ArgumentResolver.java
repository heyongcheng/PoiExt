package com.lemall.settlement.poi.reflex;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.lemall.settlement.poi.handler.model.PrimitiveValue;
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
			
			PrimitiveValue pv = resolvePrimitiveValue(name,type,request);
			if(pv.isPrimitive()){
				args[i] = pv.getValue();
				continue;
			}
			
			args[i] = resolveArgs(name,type,request);
		}
		
		return args;
	}
	
	/**
	 * 基本类型参数int, double, float, long, short, boolean, byte, char
	 * @param name
	 * @param type
	 * @param request
	 * @return
	 */
	private PrimitiveValue resolvePrimitiveValue(String name, Class<?> type, WebRequest request) {
		
		String value = request.getParameter(name);
		Object obj = null;
		boolean isPrimitive = false;
		if(StringUtils.isEmpty(value)){
			value = null;
		}
		if(int.class == type){
			isPrimitive = true;
			if(value != null){
				obj = Integer.valueOf(value).intValue();				
			}
		}
		else if(double.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Double.valueOf(value).doubleValue();
		}
		else if(float.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Float.valueOf(value).floatValue();
		}
		else if(long.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Long.valueOf(value).longValue();
		}
		else if(short.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Short.valueOf(value).shortValue();
		}
		else if(boolean.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Boolean.valueOf(value).booleanValue();
		}
		else if(byte.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Byte.valueOf(value).byteValue();
		}
		else if(char.class == type){
			isPrimitive = true;
			if(value != null){
				char[] charArray = value.toCharArray();
				if(charArray.length == 1){
					obj = charArray[0];
				}else{
					throw new RuntimeException(value + " can not convert to char");				
				}
			}
		}
		/**  包装类   **/
		else if(Integer.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Integer.valueOf(value);
		}
		else if(Double.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Double.valueOf(value);
		}
		else if(Float.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Float.valueOf(value);
		}
		else if(Long.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Long.valueOf(value);
		}
		else if(Short.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Short.valueOf(value);
		}
		else if(Boolean.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Boolean.valueOf(value);
		}
		else if(Byte.class == type){
			isPrimitive = true;
			if(value != null)
				obj = Byte.valueOf(value);
		}
		else if(Character.class == type){
			isPrimitive = true;
			if(value != null){
				char[] charArray = value.toCharArray();
				if(charArray.length == 1){
					obj = (Character)charArray[0];
				}else{
					throw new RuntimeException(value + " can not convert to Character");				
				}
			}
		}
		PrimitiveValue p = new PrimitiveValue();
		p.setPrimitive(isPrimitive);
		p.setValue(obj);
		return p;
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
