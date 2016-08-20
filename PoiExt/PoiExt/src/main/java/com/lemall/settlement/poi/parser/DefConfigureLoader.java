package com.lemall.settlement.poi.parser;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lemall.settlement.poi.configure.ConfigureLoader;
import com.lemall.settlement.poi.configure.model.Config;
import com.lemall.settlement.poi.exception.ExcelHandleException;

public class DefConfigureLoader extends ConfigureLoader{
	
	Logger LOG = LoggerFactory.getLogger(DefConfigureLoader.class);
	/**
	 * 解析配置文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Config[] parse(File file) {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			List<Element> elements = root.elements();
			Config[] configs = new Config[elements.size()];
			Iterator<Element> iterator = elements.iterator();
			int index = 0;
			while(iterator.hasNext()){
				configs[index++] = parseConfig(iterator.next());
			}
			if(LOG.isDebugEnabled()){
				LOG.debug("parse excel config:" + file.getName() + " success");			
			}
			return configs;
		} catch (Exception e) {
			throw new ExcelHandleException("parse excel config error:" + file.getName(), e);
		}
	}
	/**
	 * 解析文件
	 * @param node
	 * @return
	 */
	private Config parseConfig(Element node) {
		Config config = new Config();
		return null;
	}

}
