package com.lemall.settlement.poi.excel.processor.imports;

import com.lemall.settlement.poi.excel.model.ExcelData;

public interface ImportData {
	/**
	 * 数据 验证
	 * @param id
	 * @param excelData
	 * @param args
	 */
	public void validata(String id,ExcelData excelData,Object ...args);
	
	/**
	 * 数据持久化
	 * @param id
	 * @param excelData
	 * @param args
	 */
	public void persistence(String id,ExcelData excelData,Object ...args);
}
