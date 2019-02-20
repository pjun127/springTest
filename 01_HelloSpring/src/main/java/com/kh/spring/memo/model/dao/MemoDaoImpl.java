package com.kh.spring.memo.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemoDaoImpl implements MemoDao {

	@Autowired
	SqlSessionTemplate sqlSession;

	@Override
	public List<Map<String, String>> memoList() {
		
		return sqlSession.selectList("memo.memoList");
	}

	@Override
	public int insertMemo(Map<String, String> map) {
		
		return sqlSession.insert("memo.insertMemo",map);
	}
	
	
	
	
}
