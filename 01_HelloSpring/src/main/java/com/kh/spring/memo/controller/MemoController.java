package com.kh.spring.memo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		
		List<Map<String,String>> memoList = service.memoList();
		
		logger.debug(memoList);
		
		mv.addObject("memoList",memoList);
		mv.setViewName("/memo/memo");
		
		return mv;
	}
	
	@RequestMapping("/memo/insertMemo.do")
	public ModelAndView insertMemo(String memo, String password) {
		
		ModelAndView mv = new ModelAndView();
		
		Map<String,String> map = new HashMap();
		
		map.put("memo", memo);
		map.put("password", password);
		
		int result = service.insertMemo(map);
		
		mv.setViewName("redirect:/memo/memo.do");
		
		return mv;
	}
}
