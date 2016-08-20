package com.lemall.settlement.poi.exception;

public class ExcelHandleException extends RuntimeException {
	
	private static final long serialVersionUID = -1316949595622917647L;

	public ExcelHandleException() {
		super();
	}

	public ExcelHandleException(String message) {
		super(message);
	}

	public ExcelHandleException(Throwable cause) {
		super(cause);
	}

	public ExcelHandleException(String message, Throwable cause) {
		super(message, cause);
	}
}
