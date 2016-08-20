package com.lemall.settlement.poi.configure.parser;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lemall.settlement.poi.configure.ConfigProperties;
import com.lemall.settlement.poi.configure.ConfigureLoader;
import com.lemall.settlement.poi.configure.POIUtils;
import com.lemall.settlement.poi.configure.model.Column;
import com.lemall.settlement.poi.configure.model.Config;
import com.lemall.settlement.poi.configure.model.Export;
import com.lemall.settlement.poi.configure.model.Import;
import com.lemall.settlement.poi.configure.model.Rule;
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
	 * 解析columns
	 * @param e_columns
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Column[] parseColumns(Element e_columns) {
		if(e_columns == null)
			return null;
		/**
		 * 获取 column 元素
		 */
		List<Element> e_column_s = e_columns.elements(ConfigProperties.column);
		if(e_column_s == null || e_column_s.isEmpty()){
			return new Column[0];
		}
		/**
		 * 解析 column 配置
		 */
		int size = e_column_s.size();
		Column[] columns = new Column[size];
		for(int i=0;i<size;i++){
			columns[i] = parseColumn(e_column_s.get(i));
			if(columns[i] != null)
				columns[i].setIndex(i);
		}
		return columns;
	}
	/**
	 * 解析column
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Column parseColumn(Element e_column) {
		if(e_column == null)
			return null;
		Column column = new Column();
		/**  列名称   **/
		column.setName(POIUtils.getStringValue(e_column.attribute(ConfigProperties.name)));
		/** 列标题   **/
		column.setTitle(POIUtils.getStringValue(e_column.attribute(ConfigProperties.title)));
		/**  规则  **/
		List<Element> e_rules = e_column.elements(ConfigProperties.rule);
		if(e_rules != null && !e_rules.isEmpty()){
			int size = e_rules.size();
			Rule[] rules = new Rule[size];
			for(int i=0;i<size;i++){
				rules[i] = parseRule(e_rules.get(i));
			}
			column.setRules(rules);
		}
		return column;
	}
	/**
	 * 解析导入规则
	 * @param element
	 * @return
	 */
	private Rule parseRule(Element e_rule) {
		if(e_rule == null)
			return null;
		Rule rule = new Rule();
		/** name  **/
		rule.setName(POIUtils.getStringValue(e_rule.attribute(ConfigProperties.name)));
		/**  value  **/
		rule.setValue(POIUtils.getStringValue(e_rule.attribute(ConfigProperties.value)));
		/**  message  **/
		rule.setMessage(POIUtils.getStringValue(e_rule.attribute(ConfigProperties.message)));
		
		return rule;
	}
	/**
	 * 导入配置
	 * @param columns 
	 * @param element
	 * @return
	 */
	private Import parseImport(Element e_import, String[] columns) {
		if(e_import == null)
			return null;
		
		Import imports = new Import();
		/** startRow **/
		Integer startRow = POIUtils.getIntegerText(e_import.element(ConfigProperties.startRow));
		if(startRow != null)
			imports.setStartRow(startRow);
		/** startColumn **/
		Integer startColumn = POIUtils.getIntegerText(e_import.element(ConfigProperties.startColumn));
		if(startColumn != null)
			imports.setStartColumn(startColumn);
		/** skipRuleError **/
		Boolean skipRuleError = POIUtils.getBooleanText(e_import.element(ConfigProperties.skipRuleError));
		if(skipRuleError != null)
			imports.setSkipRuleError(skipRuleError);
		/** processor **/
		Element e_processor = e_import.element(ConfigProperties.processor);
		if(e_processor != null){
			/** bean **/
			imports.setProcessor(POIUtils.getStringValue(e_processor.attribute(ConfigProperties.ref)));
			/** method **/
			imports.setMethod(POIUtils.getStringValue(e_processor.attribute(ConfigProperties.method)));
		}
		/**  列名称   未配置时 默认为 column的全部列  **/
		String e_columns = POIUtils.getStringText(e_import.element(ConfigProperties.columns));
		if(e_columns != null){
			if(columns == null)
				throw new ExcelHandleException("parse excel config error column：[" + e_columns + "] is not exist in columns");
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
			imports.setColumns(c_columns);
		}else{
			imports.setColumns(columns);
		}
		return imports;
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
		Integer startRow = POIUtils.getIntegerText(e_export.element(ConfigProperties.startRow));
		if(startRow != null)
			export.setStartRow(startRow);
		/** startColumn **/
		Integer startColumn = POIUtils.getIntegerText(e_export.element(ConfigProperties.startColumn));
		if(startColumn != null)
			export.setStartColumn(startColumn);
		/** template **/
		export.setTemplate(POIUtils.getStringText(e_export.element(ConfigProperties.template)));
		/** createTitle **/
		Boolean createTitle = POIUtils.getBooleanText(e_export.element(ConfigProperties.createTitle));
		if(createTitle != null)
			export.setCreateTitle(createTitle);
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
			if(columns == null)
				throw new ExcelHandleException("parse excel config error column：[" + e_columns + "] is not exist in columns");
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
