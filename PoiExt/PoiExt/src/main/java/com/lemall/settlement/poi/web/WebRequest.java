package com.lemall.settlement.poi.web;

import java.util.Locale;
import java.util.Map;

public interface WebRequest {
	
	Map<String, String[]> getParameterMap();
	
	String getParameter(String paramName);
	
	String[] getParameterValues(String paramName);
	
	Locale getLocale();
}
