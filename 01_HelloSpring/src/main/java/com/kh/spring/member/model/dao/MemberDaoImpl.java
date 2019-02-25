package com.kh.spring.member.model.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	SqlSessionTemplate sqlSession;

	@Override
	public Map<String,String> memberLogin(Map<String, String> map) {
		
		return sqlSession.selectOne("member.memberLogin",map);
	}

	@Override
	public int memberEnroll(Member m) {
		
		return sqlSession.insert("member.insertMember",m);
	}

	@Override
	public Member selectOne(String userId) {
		
		return sqlSession.selectOne("member.selectOne",userId);
	}

	@Override
	public int memberUpdate(Member m) {
		
		return sqlSession.update("member.memberUpdate",m);
	}

	@Override
	public int checkId(String userId) {
		
		return sqlSession.selectOne("member.checkId",userId);
	}
	
	
	
}
