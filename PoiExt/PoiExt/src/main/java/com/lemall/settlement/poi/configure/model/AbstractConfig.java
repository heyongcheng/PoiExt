package com.lemall.settlement.poi.configure.model;

import lombok.Data;

@Data
public abstract class AbstractConfig {
	/**
	 * 开始行
	 */
	int startRow = 1;
	/**
	 * 开始列
	 */
	int startColumn = 1;
	/**
	 * 处理bean
	 */
	String processor;
	/**
	 * 方法名
	 */
	String method;
	/**
	 * sheet编号
	 */
	int sheetIndex;
	/**
	 * sheet名称
	 */
	String sheetName;
	/**
	 * 列名稱
	 */
	String[] columns;
}
