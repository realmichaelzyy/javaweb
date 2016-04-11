package com.netease.course.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.netease.course.meta.MinUser;
import com.netease.course.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, 
			Object handler) throws Exception {
		System.out.println(request.getRequestURI());
        if(request.getRequestURI().equals("/")
        	|| request.getRequestURI().equals("/login")
        	|| request.getRequestURI().equals("/show")
        	|| request.getRequestURI().equals("/logout"))
        	return true;
        String userName =  (String)request.getSession().getAttribute("userName");   
        if(userName == null){
            response.sendRedirect("/login");
            return false;
        }
		return true;
	} 

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) 
			throws Exception {
		String userName = (String) request.getSession().getAttribute("userName");
		if(userName != null && modelAndView != null)
			modelAndView.addObject("user", new MinUser(userName, userServiceImpl.getUserByName(userName).getUserType()));
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)  
			throws Exception {
		//
	}

}
