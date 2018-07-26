package com.lijin.mylab.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBackTest {

	private static final Logger logger = LoggerFactory.getLogger(LogBackTest.class);
	
	@Test
	public void test() {
		logger.info("Hello logbackxxxxx!!");
		logger.info("asdfasdf logback222!!");
	}
	
}
