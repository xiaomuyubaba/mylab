package com.lijin.mylab.controller;

import com.lijin.mylab.constants.Constant;
import com.lijin.mylab.entity.AjaxResult;
import com.lijin.mylab.entity.Pager;
import com.lijin.mylab.enums.AjaxRespEnums;
import com.lijin.mylab.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.lijin.mylab.bo.CommonBO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Map;

public abstract class BaseController {

	@Autowired
	protected CommonBO commonBO;

	protected AjaxResult buildSuccResp() {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
		return rst;
	}

	protected AjaxResult buildSuccResp(String resultDataKey, Object resultData) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
		if (!StringUtil.isEmpty(resultDataKey) && resultData != null) {
			rst.addResultData(resultDataKey, resultData);
		}
		return rst;
	}

	protected AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(ajaxResp.getRespCode());
		rst.setRespMsg(ajaxResp.getRespMsg());
		return rst;
	}

	protected AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp, String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(ajaxResp.getRespCode());
		if (StringUtil.isBlank(msg)) {
			rst.setRespMsg(ajaxResp.getRespMsg());
		} else {
			rst.setRespMsg(msg);
		}
		return rst;
	}

	protected AjaxResult buildErrorResp() {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
		return rst;
	}

	protected AjaxResult buildErrorResp(String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		if (StringUtil.isEmpty(msg)) {
			rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
		} else {
			rst.setRespMsg(msg);
		}
		return rst;
	}

	protected Map<String, String> getQryParamMap() {
		return commonBO.getRequestAttribute(Constant.REQ_KEY_QRY_PARAM_MAP);
	}

	/**
	 * 对lst进行分页
	 * @param lst
	 * @param pageNum
	 * @param pageSize
	 * @param <T>
	 * @return
	 */
	protected <T> Pager<T> buildPager(List<T> lst, int pageNum, int pageSize) {
		Pager<T> p = new Pager<>(pageNum, pageSize);
		if (lst != null && !lst.isEmpty()) {
			p.setTotal(lst.size());
			int start = p.getStartNum();
			int end = start + pageSize;
			if (end > lst.size()) {
				end = lst.size();
			}
			p.setResultLst(lst.subList(start, end));
		}
		return p;
	}
}
