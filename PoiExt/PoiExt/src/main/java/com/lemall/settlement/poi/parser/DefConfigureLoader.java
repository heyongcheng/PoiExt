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
import com.lemall.settlement.poi.configure.model.Column;
import com.lemall.settlement.poi.configure.model.Config;
import com.lemall.settlement.poi.configure.model.Export;
import com.lemall.settlement.poi.configure.model.Import;
import com.lemall.settlement.poi.constant.ConfigProperties;
import com.lemall.settlement.poi.exception.ExcelHandleException;
import com.lemall.settlement.poi.util.POIUtils;

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
				Config config = parseConfig(iterator.next());
				config.setFilePath(file.getPath());
				configs[index++] = config;
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
	private Config parseConfig(Element e_config) {
		Config config = new Config();
		try {
			/** id  **/
			config.setId(POIUtils.getStringValue(e_config.attribute(ConfigProperties.id)));
			/** columns 子元素 **/
			config.setColumns(parseColumns(e_config.element(ConfigProperties.columns)));
			/**  列名称  **/
			String[] columnNames = null;
			if(config.getColumns() != null){
				Column[] columns = config.getColumns();
				columnNames = new String[columns.length];
				for(int i=0;i<columns.length;i++){
					columnNames[i] = columns[i].getName();
				}
			}
			/**  import 子元素   **/
			config.setImportConfig(parseImport(e_config.element(ConfigProperties.imports),columnNames));
			/**  export 子元素  **/
			config.setExportConfig(parseExport(e_config.element(ConfigProperties.export),columnNames));
			
		} catch (Exception e) {
			throw new ExcelHandleException("parse excel config error:" + e_config.toString(), e);
		}
		return config;
	}
	/**
	 * 解析列属性
	 * @param element
	 * @return
	 */
	private Column[] parseColumns(Element e_column) {
		return null;
	}
	/**
	 * 导入配置
	 * @param columns 
	 * @param element
	 * @return
	 */
	private Import parseImport(Element e_import, String[] columns) {
		return null;
	}
	/**
	 * 导出配置
	 * @param element
	 * @param columns 
	 * @return
	 */
	private Export parseExport(Element e_export, String[] columns) {
		
		if(e_export == null)
			return null;
		
		Export export = new Export();
		/** startRow **/
		export.setStartRow(POIUtils.getIntegerText(e_export.element(ConfigProperties.startRow)));
		/** startColumn **/
		export.setStartColumn(POIUtils.getIntegerText(e_export.element(ConfigProperties.startColumn)));
		/** template **/
		export.setTemplate(POIUtils.getStringText(e_export.element(ConfigProperties.template)));
		/** createTitle **/
		export.setCreateTitle(POIUtils.getBooleanText(e_export.element(ConfigProperties.createTitle)));
		/** processor **/
		Element e_processor = e_export.element(ConfigProperties.processor);
		if(e_processor != null){
			/** bean **/
			export.setProcessor(POIUtils.getStringValue(e_processor.attribute(ConfigProperties.ref)));
			/** method **/
			export.setMethod(POIUtils.getStringValue(e_processor.attribute(ConfigProperties.method)));
		}
		/**  列名称   未配置时 默认为 column的全部列  **/
		String e_columns = POIUtils.getStringText(e_export.element(ConfigProperties.columns));
		if(e_columns != null){
			/** 验证列名称是否存在  **/
			String[] c_columns = e_columns.split(ConfigProperties.columnSeparator);
			boolean exist;
			for(int i=0;i<c_columns.length;i++){
				exist = false;
				for(int j=0;j<columns.length;j++){
					if(c_columns[i].equals(columns[j])){
						exist = true;
						break;
					}
				}
				if(!exist)
					throw new ExcelHandleException("parse excel config error column：" + c_columns[i] + " is not exist in columns");
			}
			export.setColumns(c_columns);
		}else{
			export.setColumns(columns);
		}
		return export;
	}
}
