package com.lijin.mylab.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AjaxResult implements Serializable {

	private static final long serialVersionUID = 6413635116040866773L;
	
	private String respCode;
	private String respMsg;
	private Map<String, Object> respData;
	
	public void addResultData(String key, Object o) {
		if (respData == null) {
			respData = new HashMap<String, Object>();
		}
		respData.put(key, o);
	}

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

	public Map<String, Object> getRespData() {
		return respData;
	}

	public void setRespData(Map<String, Object> respData) {
		this.respData = respData;
	}
}
