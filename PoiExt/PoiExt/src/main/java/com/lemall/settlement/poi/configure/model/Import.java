package com.lemall.settlement.poi.configure.model;

import lombok.Data;

@Data
public class Import extends AbstractConfig{
	
	/**
	 * 规则验证失败时返回（为false时返回）
	 */
	boolean skipRuleError;
}
