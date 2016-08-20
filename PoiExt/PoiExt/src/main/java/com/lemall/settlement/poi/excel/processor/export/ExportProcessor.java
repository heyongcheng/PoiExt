package com.lemall.settlement.poi.excel.processor.export;

import java.io.ByteArrayOutputStream;

import com.lemall.settlement.poi.configure.ConfigContext;
import com.lemall.settlement.poi.configure.model.Config;
import com.lemall.settlement.poi.configure.model.Export;
import com.lemall.settlement.poi.excel.ExcelContext;
import com.lemall.settlement.poi.excel.ExcelUtils;
import com.lemall.settlement.poi.excel.processor.Processor;
import com.lemall.settlement.poi.exception.ExcelHandleException;
import com.lemall.settlement.poi.progressor.HandleProgressor;
import com.lemall.settlement.poi.utils.ReflexUtils;

public class ExportProcessor implements Processor{
	
	/**
	 * 导出
	 * @param id
	 * @param obj
	 * @return
	 */
	public ByteArrayOutputStream export(String id,Object ...args){
		/**
		 * 获取excel配置信息
		 */
		Config config = ConfigContext.CONFIG_MAP.get(id);
		
		if(config == null)
			throw new ExcelHandleException("can not find excel config:" + id);
		
		Export export = config.getExportConfig();
		if(export == null)
			throw new ExcelHandleException("excel config:" + id + " has not export config");
		/**
		 * 获取导出bean
		 */
		String processor = export.getProcessor();
		if(processor == null)
			throw new ExcelHandleException("excel config:" + id + " export processor is null");
		/**  获取bean  **/
		Object bean = ExcelContext.getBean(processor);
		if(bean == null)
			throw new ExcelHandleException("export processor: " + processor + " bean is not exist");
		/**
		 * 导出bean's method
		 */
		String method = export.getMethod();
		if(method == null)
			throw new ExcelHandleException("excel config:" + id + " export processor: " + processor + "'s method is null");
		
		return ExcelUtils.createExcel(ReflexUtils.invokeMethod(bean,method,args), config, new HandleProgressor());
	}
	
}
