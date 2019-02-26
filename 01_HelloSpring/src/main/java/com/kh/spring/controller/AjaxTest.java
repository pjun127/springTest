package com.kh.spring.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller
public class AjaxTest {
	
	@Autowired
	BoardService boardService;
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/ajaxTest1.do")
	@ResponseBody
	public List ajaxTest1() {
		
		//List<Map<String,String>> 컨버팅 할때 자료형을 바꿀수가 없어서 제네릭 표현 하지 말아야 된다.
		List list = boardService.selectBoardList(1,5);
		
		return list;
	}
		
	@RequestMapping("/ajaxTest2.do")
	@ResponseBody
	public Member ajaxTest2(String userId){
		Member m = memberService.selectOne(userId);
		
		return m;
	}
	
	@RequestMapping("/ajaxTest3.do")
	@ResponseBody
	public Map ajaxTest3(int no) {
		
		Map map = boardService.selectBoard(no);
		
		return map;
		
	}
	

}
