package com.kh.spring.member.model.service;

import java.util.Map;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	
	Map<String,String> memberLogin(Map<String,String> map);
	int memberEnroll(Member m);
	Member selectOne(String userId);
	int memberUpdate(Member m);
}
