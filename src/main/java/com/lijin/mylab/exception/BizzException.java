package com.lijin.mylab.exception;

public class BizzException extends RuntimeException {

	private static final long serialVersionUID = 4890373206371030539L;
	
	private String errorCode;
	
	private String errorMsg;

	public BizzException(String errorMsg) {
		this("",errorMsg);
	}


	public BizzException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public BizzException(String errorMsg,Throwable e) {
		this("",errorMsg,e);
	}


	public BizzException(String errorCode, String errorMsg,Throwable e) {
		super(errorMsg,e);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public String getErrorMsg() {
		return errorMsg;
	}
	
	public String toString(){
		return "BizzException["+errorCode+"]["+errorMsg+"]";
	}
	
}
