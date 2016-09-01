package com.lemall.settlement.poi.handler;

import com.lemall.settlement.poi.web.WebRequest;

public interface DataHandler {
	
	Object handle(Object bean,String method,WebRequest request);
	
}
