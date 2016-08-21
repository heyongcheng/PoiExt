package com.lemall.settlement.poi.reflex.model;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * 
 * @author heyongcheng
 *
 */
@Data
public class MethodInfo {
	/**
	 * 方法
	 */
	Method method;
	/**
	 * 参数类型
	 */
	Class<?>[] paramTypes;
	/**
	 * 参数名称
	 */
	String[] paramNames;
}
