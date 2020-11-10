package com.lijin.mylab;

import javax.servlet.http.HttpServletRequest;

import com.lijin.mylab.enums.AjaxRespEnums;
import com.lijin.mylab.utils.StringUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lijin.mylab.controller.BaseController;
import com.lijin.mylab.entity.AjaxResult;

@ControllerAdvice(basePackageClasses = BaseController.class)
public class MyAppExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(HttpServletRequest req, Throwable t) {
		return buildExceptionResp("处理异常: " + t.getMessage());
	}

	protected AjaxResult buildExceptionResp(String msg) {
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
