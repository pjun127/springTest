package com.kh.spring.memo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.memo.model.service.MemoService;

@Controller
public class MemoController {
	
	private Logger logger = Logger.getLogger(MemoController.class);
	
	@Autowired
	MemoService service;
	
	@RequestMapping("/memo/memo.do")
	public ModelAndView memoList() {
		
		ModelAndView mv = new ModelAndView();
		
		logger.debug("메모를 찾아줘~");
		
		List<Map<String,String>> memoList = service.selectMemo();
		
		logger.debug("content" + memoList);
		
		mv.addObject("memoList",memoList);
		mv.setViewName("/memo/memo");
		
		return mv;
	}
	
	/*@RequestMapping("/memo/insertMemo.do")
	public ModelAndView insertMemo(String memo, String password) {
		
		ModelAndView mv = new ModelAndView();
		
		Map<String,String> map = new HashMap();
		
		map.put("memo", memo);
		map.put("password", password);
		
		int result = service.insertMemo(map);
		
		mv.setViewName("redirect:/memo/memo.do");
		
		return mv;
	}*/
	
	@RequestMapping("/memo/insertMemo.do")
	public String insertMemo(String memo, String password, Model model) {
		
		Map<String,String> map = new HashMap();
		
		map.put("memo", memo);
		map.put("password", password);
		
		int result = service.insertMemo(map);
		
		String msg = "";
		String loc = "/memo/memo.do";
		
		if(result>0) {
			msg = "메모 입력 성공!";
		}
		else {
			msg = "메모 입력 실패";
		}
		
		model.addAttribute("loc",loc);
		model.addAttribute("msg",msg);
		
		return "/common/msg";
	}
	
}
