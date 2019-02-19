package com.kh.spring.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class LoginInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { 
		
		String userId = (String)request.getSession().getAttribute("userId");
		
		if(userId==null) {
			String loc = "/";
			String msg = "로그인 후 이용 가능";
			
			request.setAttribute("loc", loc);
			request.setAttribute("msg", msg);
			
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}

	
}
