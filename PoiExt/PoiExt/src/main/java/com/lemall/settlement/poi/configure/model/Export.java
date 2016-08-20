package com.lemall.settlement.poi.configure.model;

import lombok.Data;

@Data
public class Export extends AbstractConfig{
	/**
	 * 导出模板文件
	 */
	String template;
	/**
	 * 是否生成标题
	 */
	boolean createTitle = true;
}
