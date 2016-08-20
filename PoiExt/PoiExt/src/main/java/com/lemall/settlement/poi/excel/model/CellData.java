package com.lemall.settlement.poi.excel.model;

import lombok.Data;

@Data
public class CellData {
	/**
	 * 行号
	 */
	int rowIndex;
	/**
	 * 列号
	 */
	int columnIndex;
	/**
	 * 名称
	 */
	String name;
	/**
	 * 标题
	 */
	String title;
	/**
	 * 值
	 */
	String value;
	/**
	 * 根据规则转换后的值
	 */
	Object refValue;
	/**
	 * 是否有效
	 */
	boolean errorFlag;
	/**
	 * 描述
	 */
	String message;
}
