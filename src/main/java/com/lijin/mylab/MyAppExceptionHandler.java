package com.lijin.mylab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lijin.mylab.bo.CommonBO;
import com.lijin.mylab.controller.BaseController;
import com.lijin.mylab.entity.AjaxResult;

@ControllerAdvice(basePackageClasses = BaseController.class)
public class MyAppExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private CommonBO commonBO;
	
	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(HttpServletRequest req, Throwable t) {
		return commonBO.buildErrorResp("处理异常: " + t.getMessage());
	}
	
}
