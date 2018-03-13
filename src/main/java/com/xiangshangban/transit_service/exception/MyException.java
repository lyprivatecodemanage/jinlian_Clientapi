package com.xiangshangban.transit_service.exception;

public class MyException extends RuntimeException {
	private String exceptionMessage;
	 public MyException(String message)
	 {
		 	super(message);
		 	setExceptionMessage(message);
	 }
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	 
}
