package com.lemall.settlement.poi.handler;

import com.lemall.settlement.poi.reflex.ArgumentResolver;
import com.lemall.settlement.poi.reflex.ReflexUtils;
import com.lemall.settlement.poi.reflex.model.MethodInfo;
import com.lemall.settlement.poi.web.WebRequest;

public class DefaultHandler implements DataHandler {

	ArgumentResolver argumentResolver = new ArgumentResolver();
	/**
	 * 方法调用
	 */
	@Override
	public Object handle(Object bean, String methodName, WebRequest request) {
		
		MethodInfo methodInfo = ReflexUtils.getMethodInfo(bean, methodName);
		
		return ReflexUtils.invokeMethod(bean, methodInfo.getMethod(), argumentResolver.resolveArguments(methodInfo, request));
	}

}
