package com.lemall.settlement.poi.excel;

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.lemall.settlement.poi.configure.ConfigContext;
import com.lemall.settlement.poi.configure.model.Config;
import com.lemall.settlement.poi.exception.ExcelHandleException;
import com.lemall.settlement.poi.progressor.Progressor;

/**
 * ExcelUtils
 * @author heyongcheng
 *
 */
public class ExcelUtils {
	
	/**
	 * 生成Excel文件
	 * @param data
	 * @param config
	 * @param progressor
	 * @return
	 */
	public static ByteArrayOutputStream createExcel(Object data,Config config,Progressor progressor){
		return null;
	}
	/**
	 * 生成Excel文件
	 * @param data
	 * @param configId
	 * @param progressor
	 * @return
	 */
	public static ByteArrayOutputStream createExcel(Object data,String configId,Progressor progressor){
		if(StringUtils.isEmpty(configId)){
			throw new ExcelHandleException("config id can not be null");
		}
		Config config = ConfigContext.CONFIG_MAP.get(configId);
		if(config == null){
			throw new ExcelHandleException("can not find excel config:" + configId);
		}
		
		return null;
	}
}
