package com.lemall.settlement.poi.configure.model;

import lombok.Data;

@Data
public class Column {
	/**
	 * 列索引
	 */
	int index;
	/**
	 * 列标题
	 */
	String title;
	/**
	 * 列名称
	 */
	String name;
	/**
	 * 列导入规则
	 */
	Rule[] rules;
	
}
