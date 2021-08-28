package com.lijin.mylab.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lijin.mylab.interceptor.MyInterceptor;

@Configuration
@ConfigurationProperties(prefix = "mylab")
public class MyAppConfig implements WebMvcConfigurer, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(MyAppConfig.class);

	private String dbUrl;
	private DBConfig db;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("##########################################dbUrl:" + dbUrl);
		System.out.println("##########################################dbUrl:" + db.getDbUrl());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public DBConfig getDb() {
		return db;
	}

	public void setDb(DBConfig db) {
		this.db = db;
	}
}
