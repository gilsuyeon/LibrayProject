package com.goodee.library.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MemberLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest req,
			HttpServletResponse resp, Object handler) throws Exception{
		
		HttpSession session = req.getSession();
		if(session != null) {
			Object obj = session.getAttribute("loginMember");
			if(obj != null)
					return true;   //return true : 컨트롤러 메소드 실행 허용한다.
		}
		resp.sendRedirect(req.getContextPath()+"/member/login");
		return false;
	}
}
