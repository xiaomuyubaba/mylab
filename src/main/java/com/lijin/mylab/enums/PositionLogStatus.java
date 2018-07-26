package com.lijin.mylab.enums;

public enum PositionLogStatus {

	_0("0", "持有中"),
	_1("1", "已卖出");
	
	private PositionLogStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	private String code;
	private String desc;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
