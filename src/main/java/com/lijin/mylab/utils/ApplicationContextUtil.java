package com.lijin.mylab.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext ctx = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		ctx = applicationContext;
	}

	@Override
	public void destroy() throws Exception {
		ctx = null;
	}

	/**
	 * 从Spring容器中按名称获取指定的Bean
	 * 
	 * @param <T>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		AssertUtil.objIsNull(ctx, "Spring容器为null");
		Object o = ctx.getBean(name);
		AssertUtil.objIsNull(o, "指定的Bean不存在:" + name);
		return (T) o;
	}
}
