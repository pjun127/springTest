package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

//세션을 어노테이션으로 등록 가능!!
//userId자리에는 Object를 넣을수 있다!!!
@SessionAttributes("userId")

@Controller
public class MemberController {
	
	private Logger logger = Logger.getLogger(MemberController.class);
	
	@Autowired
	MemberService service;
	@Autowired
	BCryptPasswordEncoder pwEncoder;
	
	@RequestMapping("/member/memberLogin.do")
	/*public String memberLogin(HttpSession session, String userId, String password, Model model) {*/
	/*public String memberLogin(HttpSession session, String userId, String password, Model model) {*/
	public ModelAndView memberLogin(HttpSession session, String userId, String password, Model model) {
		
		//model과 view를 다 포홤 함!
		ModelAndView mv = new ModelAndView();
		
		logger.debug("로그인 제대로 들어옴!");
		logger.debug("userId : "+ userId + "password : "+ password);
		
		//1.vo객체를 이용하는 방법
		//2. map!을 이용하는 방법
		Map<String,String> map = new HashMap();
		
		map.put("userId", userId);
		map.put("password", password);

		Map<String,String> result = service.memberLogin(map);
		
		logger.debug("로그인 결과"+result);
		
		String msg = "";
		String loc = "/";
		
		if(result!=null) {
			//matches 매소드를 사용하여 알아서 비교 할수 있게 함!
			if(pwEncoder.matches(password, result.get("PASSWORD"))/*result.get("PASSWORD").equals(password)*/) {
				msg="로그인 성공";
				//model.addAttribute("userId", result.get("USERID"));
				/*session.setAttribute("userId", result.get("USERID"));*/
				mv.addObject("userId", result.get("USERID"));
			}
			else {
				msg="패스워드가 일치 하지 않습니다.";
			}
		}
		else {
			msg="아이디가 존재 하지 않습니다.";
		}
		
		/*model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);*/
		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		
		mv.setViewName("common/msg");
		
		/*if(result!=null) {			
			try {
				throw new NullPointerException();
			}
			catch(Exception e){
				logger.error("내가 만든 에러"+e.getMessage());
				throw new NullPointerException();
			}
		}*/
		
		//return "common/msg";
		return mv;
	}
	
	@RequestMapping("/member/memberLogout.do")
	public String memberLogout(SessionStatus status) {
		
		//SessionAttributes 등록 로그인이면 : sessionStatus 객체의 setComplete(); 매소드 이용 로그아웃 처리
		//HttpSession을 이용 로그인이면 : HttpSession.invalide(); 를 사용하여 로그아웃 처리
		
		//!를 넣어야 완료 안됬니?
		if(!status.isComplete()) {
			status.setComplete();
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/member/memberEnroll.do")
	public String memberEnroll() {
		
		return "/member/enroll";
	}
	
	@RequestMapping("/member/memberEnrollEnd.do")
	public String memberEnrollEnd(Member m, Model model) {
		
		//사용자가 입력한 패스워드
		String rawPw = m.getPassword();
		System.out.println("암호화 전 패스워드 : "+rawPw);
		
		System.out.println("암호화 후 패스워드 : "+pwEncoder.encode(rawPw));
		
		//암호화 된 채로 등록 하기 위해서!!!
		m.setPassword(pwEncoder.encode(rawPw));
		
		int result = service.memberEnroll(m);
		
		String msg = "";
		String loc = "/";
		
		if(result>0) {
			msg="회원가입 성공";
		}
		else {
			msg="회원가입 실패";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		
		return "common/msg";
	}
	
	@RequestMapping("/member/memberView.do")
	public ModelAndView memberView(HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		
		//이렇게 할 수도 있지만 쿼리 스트링 방식으로 userId 가지고 올수도 있다.
		String userId = (String)session.getAttribute("userId");
		
		Member m = service.selectOne(userId);
		
		System.out.println(m);
		
		mv.addObject("member",m);
		mv.setViewName("member/memberView");
		
		return mv;
	}
	
	@RequestMapping("/member/memberUpdate.do")
	public ModelAndView memberUpdate(Member m) {
		
		int result = service.memberUpdate(m);
		
		String msg = "";
		String loc = "";
		
		if(result>0) {
			msg = "수정 완료";
			loc = "/";
		}
		else {
			msg = "수정 실패";
			loc = "/member/memberView.do";
		}
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		mv.setViewName("common/msg");
		
		return mv;
	}
	
	@RequestMapping("/member/checkId.do")
	/*public void checkId(String userId, HttpServletResponse response) throws IOException{
		logger.debug("중복체크");
		
		boolean isId=service.checkId(userId)==0?false:true;
		
		response.getWriter().print(isId);
		
	}*/
	public ModelAndView checkId(String userId, ModelAndView mv) throws UnsupportedEncodingException {
		
		Map map = new HashMap();
		
		boolean isId=service.checkId(userId)==0?false:true;
		
		map.put("isId", isId);
		
		List<String> list = new ArrayList();
		
		list.add("Park");
		list.add("Lee");
		list.add("Kim");
		list.add("Shin");
		
		//맵에 있는 모든 값을 다 넣는다 key / value 형식으로
		mv.addAllObjects(map);
		
		mv.addObject("char",URLEncoder.encode("문자열","UTF-8"));
		mv.addObject("num",1);
		mv.addObject("list",list);
		
		//viewName을 명시 할때 servlet-context.xml에 등록한 json id값을 입력 해야 된다.
		mv.setViewName("jsonView");
		
		return mv;
	}

}
