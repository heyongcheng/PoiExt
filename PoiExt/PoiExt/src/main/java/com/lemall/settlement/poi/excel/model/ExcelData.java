package com.lemall.settlement.poi.excel.model;

import lombok.Data;

@Data
public class ExcelData {
	
	/**
	 * 错误标识
	 */
	boolean errorFlag;
	/**
	 * 描述
	 */
	String message;
	
	/**
	 * 行数据
	 */
	RowData[] rowDatas;
}
