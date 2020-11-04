package com.lijin.mylab.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			String reqUri = req.getRequestURI();
			String handlerClass = handler.getClass().getName();
			logger.info("拦截到了[{}][{}]", reqUri, handlerClass);
		}
		return true;
	}

}
