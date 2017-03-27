package com.lemall.settlement.poi.configure;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.lemall.settlement.poi.configure.model.Config;

public interface ConfigContext {
	/**
	 * 存放解析后的配置文件
	 */
	Map<String,Config> CONFIG_MAP = new HashMap<String,Config>();
	/**
	 * 获取配置文件
	 * @param key
	 * @return
	 */
	public Config getConfig(String key);
	
	/**
	 * 解析配置文件
	 * @param file
	 * @return
	 */
	public void load(File file);
	
	/**
	 * 解析文件
	 * @param path
	 */
	public void load(String path);
}
