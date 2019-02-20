package com.kh.spring.demo.model.service;



import java.util.List;

import com.kh.spring.demo.model.vo.Dev;

public interface DemoService {
	
	int insertDev(Dev dev);
	List<Dev> selectDemoList();
	Dev selectOne(int devNo);
	int updateDevEnd(Dev dev);
	int deleteDev(int devNo);
}
