package com.lemall.settlement.poi.excel;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.lemall.settlement.poi.configure.ConfigContext;
import com.lemall.settlement.poi.configure.parser.DefConfigureLoader;
import com.lemall.settlement.poi.exception.ExcelHandleException;

public class ConfigInitializeBean implements ApplicationContextAware{
	/**
	 * 加载文件路径
	 */
	private String basePath;
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	/**
	 * 配置解析器
	 */
	private String configureLoaderClassName;
	public void setConfigureLoaderClassName(String configureLoaderClassName) {
		this.configureLoaderClassName = configureLoaderClassName;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ExcelContext.setApplicationContext(applicationContext);
		loadConfig();
	}
	/**
	 * 加载配置文件
	 * @throws IOException 
	 */
	private void loadConfig(){
		try {
			getConfigContext().load(basePath);
		} catch (Exception e) {
			throw new ExcelHandleException("load excel config error." ,e);
		}
	}
	
	/**
	 * 获取解析器
	 * @return
	 */
	private ConfigContext getConfigContext(){
		try {
			ConfigContext configContext = null;
			if(!StringUtils.isEmpty(configureLoaderClassName)){
				configContext = (ConfigContext) Class.forName(configureLoaderClassName).newInstance();
			}else{
				configContext = new DefConfigureLoader();
			}
			return configContext;
		} catch (Exception e) {
			throw new ExcelHandleException("initialization bean:" + configureLoaderClassName + " error " ,e);
		}
	}
}
