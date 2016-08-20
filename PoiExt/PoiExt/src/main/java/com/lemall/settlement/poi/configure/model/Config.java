package com.lemall.settlement.poi.configure.model;

import lombok.Data;

@Data
public class Config {
	/**
	 * 唯一标识
	 */
	String id;
	/**
	 * 配置文件地址
	 */
	String filePath;
	/**
	 * 導入配置
	 */
	Import importConfig;
	/**
	 * 導出配置
	 */
	Export exportConfig;
	/**
	 * 列信息
	 */
	Column[] columns;
}
