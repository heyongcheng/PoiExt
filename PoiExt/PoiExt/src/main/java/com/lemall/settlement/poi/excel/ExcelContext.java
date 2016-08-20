package com.lemall.settlement.poi.excel;

import org.springframework.context.ApplicationContext;

public class ExcelContext {
	
	private static ApplicationContext context;
	/**
	 * 设置context
	 * @param applicationContext
	 */
	public static void setApplicationContext(ApplicationContext applicationContext){
		context = applicationContext;
	}
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	/**
	 * 获取bean
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		if(context == null)
			return null;
		return context.getBean(beanName);
	}
	/**
	 * 查询bean
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String beanName,Class<T> clazz){
		if(context == null)
			return null;
		return context.getBean(beanName, clazz);
	}
}
