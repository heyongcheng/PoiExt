package com.lemall.settlement.poi.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;

public class POIUtils {
	
	/**
	 * 元素text是否为空
	 * @param element
	 * @return
	 */
	public static boolean isEmptyText(Element element){
		if(element == null)
			return true;
		if(StringUtils.isEmpty(element.getText())){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取元素String值
	 * @param element
	 * @return
	 */
	public static String getStringText(Element element){
		if(isEmptyText(element)){
			return null;
		}
		return element.getText();
	}
	/**
	 * 获取元素integer值
	 * @param element
	 * @return
	 */
	public static Integer getIntegerText(Element element){
		if(isEmptyText(element)){
			return null;
		}
		return Integer.valueOf(element.getText());
	}
	/**
	 * 获取元素boolean值
	 * @param element
	 * @return
	 */
	public static Boolean getBooleanText(Element element){
		if(isEmptyText(element)){
			return null;
		}
		return Boolean.valueOf(element.getText());
	}
	
	/**
	 * 判断属性是否为空
	 * @param attribute
	 * @return
	 */
	public static boolean isEmptyValue(Attribute attribute){
		if(attribute == null)
			return true;
		if(StringUtils.isEmpty(attribute.getValue())){
			return true;
		}
		return false;
	}
	/**
	 * 获取属性String值
	 * @param attribute
	 * @return
	 */
	public static String getStringValue(Attribute attribute){
		if(isEmptyValue(attribute)){
			return null;
		}
		return attribute.getValue();
	}
	/**
	 * 获取属性integer值
	 * @param attribute
	 * @return
	 */
	public static Integer getIntegerValue(Attribute attribute){
		if(isEmptyValue(attribute)){
			return null;
		}
		return Integer.valueOf(attribute.getValue());
	}
	/**
	 * 获取属性boolean值
	 * @param element
	 * @return
	 */
	public static Boolean getBooleanValue(Attribute attribute){
		if(isEmptyValue(attribute)){
			return null;
		}
		return Boolean.valueOf(attribute.getValue());
	}
	
}
