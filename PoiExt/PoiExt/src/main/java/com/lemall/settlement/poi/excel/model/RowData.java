package com.lemall.settlement.poi.excel.model;

import java.util.Map;

import lombok.Data;

@Data
public class RowData {
	/**
	 * 行号
	 */
	int index;
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
	Map<String,CellData>[] cellDatas;
}
