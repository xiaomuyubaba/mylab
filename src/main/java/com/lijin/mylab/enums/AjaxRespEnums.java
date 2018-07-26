package com.lijin.mylab.enums;

public enum AjaxRespEnums {

	SUCC("00", "操作成功"),
	ERROR("ZZ", "系统异常，请联系管理员");
	
	private AjaxRespEnums(String respCode, String respMsg) {
		this.respCode = respCode;
		this.respMsg = respMsg;
	}
	
	private String respCode;
	private String respMsg;
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
}
