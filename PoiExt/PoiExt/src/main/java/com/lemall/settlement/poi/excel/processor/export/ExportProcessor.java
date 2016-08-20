package com.lemall.settlement.poi.excel.processor.export;

import java.io.ByteArrayOutputStream;

import com.lemall.settlement.poi.configure.ConfigContext;
import com.lemall.settlement.poi.configure.model.Config;
import com.lemall.settlement.poi.configure.model.Export;
import com.lemall.settlement.poi.excel.processor.Processor;
import com.lemall.settlement.poi.exception.ExcelHandleException;

public abstract class ExportProcessor implements Processor{
	
	/**
	 * 导出
	 * @param id
	 * @param obj
	 * @return
	 */
	public <T> ByteArrayOutputStream export(String id,Object ...args){
		/**
		 * 获取excel配置信息
		 */
		Config config = ConfigContext.CONFIG_MAP.get(id);
		
		if(config == null)
			throw new ExcelHandleException("can not find excel config:" + id);
		
		Export export = config.getExportConfig();
		if(export == null)
			throw new ExcelHandleException("excel config:" + id + " has not export config");
		
		String processor = export.getProcessor();
		
		
		
		
		return null;
	}
	
}
