package com.kh.spring;

import org.apache.log4j.Logger;

import com.kh.spring.demo.model.vo.Dev;

public class Log4jTest {
	
	//Logger.getLogger(logger 찍을 클래스 명)
	private Logger logger = Logger.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {
		
		new Log4jTest().test();
	}
	
	public void test() {
		Dev dev = new Dev();
		dev.setDevName("박종언");
		dev.setDevEmail("p@na");

		logger.fatal("fatal임!");
		logger.error("error임!");
		logger.warn("warn임!");
		logger.info("info임!");
		logger.debug("debug임!"+dev);
		
	}

}
