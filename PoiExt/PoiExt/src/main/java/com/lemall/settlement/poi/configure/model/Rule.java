package com.lemall.settlement.poi.configure.model;

import lombok.Data;

@Data
public class Rule {
	/**
	 * 规则名称
	 */
	String name;
	/**
	 * 规则值
	 */
	String[] values;
	/**
	 * 错误信息
	 */
	String message;
}
