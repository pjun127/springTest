package com.kh.spring.memo.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.memo.model.dao.MemoDao;

@Service
public class MemoServiceImpl implements MemoService {

	@Autowired
	MemoDao dao;

	@Override
	public List<Map<String, String>> selectMemo() {
		
		return dao.selectMemo();
	}

	@Override
	public int insertMemo(Map<String, String> map) {
		
		return dao.insertMemo(map);
	}
	
	
	
	
	
}
