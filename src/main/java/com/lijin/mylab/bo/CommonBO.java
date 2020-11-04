package com.lijin.mylab.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lijin.mylab.entity.AjaxResult;
import com.lijin.mylab.entity.IEntityTransfer;
import com.lijin.mylab.enums.AjaxRespEnums;
import com.lijin.mylab.utils.BeanUtil;
import com.lijin.mylab.utils.StringUtil;

@Service
public class CommonBO {

	public <T> List<Map<String, String>> transferLst(List<T> lst, IEntityTransfer transfer) {

		List<Map<String, String>> resultLst = new ArrayList<Map<String, String>>();
		if (lst != null) {
			for (int i = 0; i < lst.size(); i ++) {
				T o = lst.get(i);
				Map<String, String> m = BeanUtil.desc(o, null, null);
				if (transfer != null) {
					transfer.transfer(m);
				}
				resultLst.add(m);
			}
		}
		return resultLst;
	}
	
	public <T> Map<String, String> transferEntity(T entity, IEntityTransfer transfer) {
		Map<String, String> m = BeanUtil.desc(entity, null, null);
		if (transfer != null) {
			transfer.transfer(m);
		}
		return m;
	}

	public AjaxResult buildSuccResp() {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
		return rst;
	}

	public AjaxResult buildSuccResp(String resultDataKey, Object resultData) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
		if (!StringUtil.isEmpty(resultDataKey) && resultData != null) {
			rst.addResultData(resultDataKey, resultData);
		}
		return rst;
	}

	public AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(ajaxResp.getRespCode());
		rst.setRespMsg(ajaxResp.getRespMsg());
		return rst;
	}

	public AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp, String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(ajaxResp.getRespCode());
		if (StringUtil.isBlank(msg)) {
			rst.setRespMsg(ajaxResp.getRespMsg());
		} else {
			rst.setRespMsg(msg);
		}
		return rst;
	}

	public AjaxResult buildErrorResp() {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
		return rst;
	}

	public AjaxResult buildErrorResp(String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		if (StringUtil.isEmpty(msg)) {
			rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
		} else {
			rst.setRespMsg(msg);
		}
		return rst;
	}
	
}
