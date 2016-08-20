package com.lemall.settlement.poi.excel.processor.export;

import java.util.List;

public interface ExportData {
	/**
	 * 获取导出数据
	 * @param id
	 * @param args
	 * @return
	 */
	public List<Object> getExportData(String id,Object ...args);
	
}
