package com.kh.spring.demo.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.demo.model.service.DemoService;
import com.kh.spring.demo.model.vo.Dev;

@Controller
public class DemoController {
	
	@Autowired
	DemoService service;
	
	//()안에는 url 매핑 값을 적어놓는다
	@RequestMapping("/demo/demo.do")
	public String demo() {
		/*service.test();*/
		
		//prefix+return값+suffix 이렇게 붙는다!!!
		return "demo/demo";
	}
	
	@RequestMapping("/demo/demo1.do")
	public String demo1(HttpServletRequest request) {
		//@RequestParam(value="devAge") int age = int devAge 이렇게 하면 파라미터 값으로 가지고 오고 age라는 변수명에 저장 된다
		
		String devName = request.getParameter("devName");
		int devAge = Integer.parseInt(request.getParameter("devAge"));
		String devEmail = request.getParameter("devEmail");
		String devGender = request.getParameter("devGender");
		String[] devLang = request.getParameterValues("devLang");
		
		Dev dev = new Dev(devName, devAge, devEmail, devGender, devLang);
		
		request.setAttribute("dev", dev);
		
		//System.out.println(devName + devAge + devEmail + devGender + devLang);
		
		return "demo/result";
	}

	@RequestMapping("/demo/demo2.do")
	//@RequestParam 에서 무조건 받지 않아도 되는 경우 required=false를 하여 사용 한다.
	public String demo2(HttpServletRequest req, 
			@RequestParam(value="devName") String devName,
			@RequestParam(value="devAge", required=false, defaultValue="19") int devAge,
			@RequestParam(value="devEmail", required=false) String devEmail,
			@RequestParam(value="devGender", required=false) String devGender,
			@RequestParam(value="devLang", required=false) String[] devLang)
	
	//form에서 넘어오는 Name이랑 변수명이 같으면 @RequestParam을 안 기입 해도 된다!
	//@RequestParam을 안 썼을 경우 무조건 값이 있어야 된다 하나라도 없으면 bad request 뜸
	
	/*public String demo2(HttpServletRequest req, 
			String devName,
			int devAge,
			String devEmail,
			String devGender,
			String[] devLang)*/{
		
		req.setAttribute("dev", new Dev(devName, devAge, devEmail, devGender, devLang));
		
		//System.out.println(devName + devAge + devEmail + devGender + devLang);
		
		return "demo/result";
	}
	
	//model은 객체를 담아서 보내는 역할을 함 request.setAttribute()
	//데이터를 공용으로 쓸수 있는 객체가 model이라고 함
	@RequestMapping("/demo/demo3.do")
	public String demo3(Dev dev, Model model) {
		
		//System.out.println(dev);
		
		model.addAttribute("dev",dev);
		
		//System.out.println(model);
		
		return "demo/result";
	}
	
	@RequestMapping("/demo/insertDev.do")
	public String insertDev(Dev dev) {
		int result = service.insertDev(dev);
		
		return "redirect:/";
	}
	
	//model은 객체를 받아서 공용객체로 사용하는 방법 request와 같음
	@RequestMapping("/demo/selectDemoList.do")
	public String selectDemoList(Model model) {
		
		List<Dev> list = service.selectDemoList();
		
		model.addAttribute("list",list);
		
		return "demo/devList";
	}
	
	@RequestMapping("/demo/updateDev.do")
	public String updateDev(int devNo, Model model) {
		
		Dev dev = service.selectOne(devNo);
		
		model.addAttribute("dev",dev);
		
		return "demo/form";
	}
	
	@RequestMapping("/demo/updateDevEnd")
	public String updateDevEnd(Dev dev, Model model) {
		
		int result = service.updateDevEnd(dev);
		
		return "redirect:/demo/selectDemoList.do";
	}

}
