package com.kh.spring.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.demo.model.dao.DemoDao;
import com.kh.spring.demo.model.vo.Dev;

@Service
public class DemoServiceImpl implements DemoService{
	
	@Autowired
	DemoDao dao;

	@Override
	public int insertDev(Dev dev) {
		int result = dao.insertDev(dev);
		return result;
	}

	@Override
	public List<Dev> selectDemoList() {
		return dao.selectDemoList();
	}

	@Override
	public Dev selectOne(int devNo) {
		
		return dao.selectOne(devNo);
	}

	@Override
	public int updateDevEnd(Dev dev) {
		
		return dao.updateDevEnd(dev);
	}

	@Override
	public int deleteDev(int devNo) {
		
		return dao.deleteDev(devNo);
	}

}
