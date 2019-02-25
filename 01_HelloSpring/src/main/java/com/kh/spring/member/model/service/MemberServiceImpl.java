package com.kh.spring.member.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao dao;

	@Override
	public Map<String,String> memberLogin(Map<String, String> map) {
		
		return dao.memberLogin(map);
	}

	@Override
	public int memberEnroll(Member m) {
		
		return dao.memberEnroll(m);
	}

	@Override
	public Member selectOne(String userId) {
		
		return dao.selectOne(userId);
	}

	@Override
	public int memberUpdate(Member m) {
		
		return dao.memberUpdate(m);
	}

	@Override
	public int checkId(String userId) {
		
		return dao.checkId(userId);
	}

	
	
	

}
