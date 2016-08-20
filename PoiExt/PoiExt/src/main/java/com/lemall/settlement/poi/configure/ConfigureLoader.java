package com.lemall.settlement.poi.configure;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lemall.settlement.poi.configure.model.Config;
import com.lemall.settlement.poi.exception.ExcelHandleException;

/**
 * 配置加载器
 * @author HE
 *
 */
public abstract class ConfigureLoader implements ConfigContext{
	
	Logger LOG = LoggerFactory.getLogger(ConfigureLoader.class);
	
	/**
	 * 获取配置
	 */
	@Override
	public Config getConfig(String key) {
		Config config = CONFIG_MAP.get(key);
		if(LOG.isDebugEnabled()){
			LOG.debug("get excel config:{}, {}",key,config == null ? null : config.toString());
		}
		return config;
	}
	/**
	 * 添加配置
	 * @param config
	 */
	public void addConfig(Config config){
		if(config == null){
			return;
		}
		if(StringUtils.isEmpty(config.getId())){
			throw new ExcelHandleException("excel config:" + config.getFilePath() + " attribute:id can not be null");
		}
		if(CONFIG_MAP.containsKey(config.getId())){
			throw new ExcelHandleException("excel config:" + config.getId() + " id duplicate");
		}
		CONFIG_MAP.put(config.getId(), config);
		if(LOG.isInfoEnabled()){
			LOG.info("excel config：{} has been added",config.getId());
		}
	}
	/**
	 * 加载配置文件
	 */
	@Override
	public void load(File file) {
		if(file == null){
			return;
		}
		if(LOG.isDebugEnabled()){
			LOG.debug("load excel config file:{},{}",file.getParentFile(),file.getName());
		}
		Config[] configs = parse(file);
		for(int i=0;i<configs.length;i++){
			addConfig(configs[i]);			
		}
	}
	/**
	 * 加載路徑
	 * @param path
	 */
	public void load(String path){
		try {
			File file = new File(path);
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if(files[i].isDirectory()){
						load(file.getPath());
					}else if(files[i].isFile()){
						load(file);
					}
				}
			}else{
				load(file);
			}
		} catch (Exception e) {
			throw new ExcelHandleException("load excel config filePath:" + path + " error",e);
		}
	}
	
	/**
	 * 解析配置文件
	 * @param file
	 * @return
	 */
	public abstract Config[] parse(File file);
	
}
