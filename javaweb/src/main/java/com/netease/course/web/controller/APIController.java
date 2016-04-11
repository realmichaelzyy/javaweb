package com.netease.course.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.course.service.impl.LoginServiceImpl;
import com.netease.course.service.impl.UserServiceImpl;

@Controller
@RequestMapping(value = "/api")
public class APIController {

	@Autowired
	private LoginServiceImpl loginServiceImpl;
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap login(@RequestParam String userName, 
			 @RequestParam String password,
			 HttpServletResponse response,
			 HttpSession session) {
		ModelMap map = new ModelMap();
		int loginStatus = loginServiceImpl.login(userName, password);
		if(loginStatus == LoginServiceImpl.LOGIN_SUCCESS) {
			session.setAttribute("userName", userName);
			map.addAttribute("message", LoginServiceImpl.LOGIN_SUCCESS_MESSAGE);
			map.addAttribute("result", true);
			map.addAttribute("code", 200);
		} 
		else if(loginStatus == LoginServiceImpl.LOGIN_USERNAME_NOT_EXIST) {
			map.addAttribute("message", LoginServiceImpl.LOGIN_USERNAME_NOT_EXIST_MESSAGE);
			map.addAttribute("result", false);
			map.addAttribute("code", 400);
		}
		else if(loginStatus == LoginServiceImpl.LOGIN_PASSWORD_ERROR) {
			map.addAttribute("message", LoginServiceImpl.LOGIN_PASSWORD_ERROR_MESSAGE);
			map.addAttribute("result", false);
			map.addAttribute("code", 400);
		}
		return map;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap delete(@RequestParam int id, 
			 HttpServletResponse response,
			 HttpSession session) {
		ModelMap map = new ModelMap();
		String userName = (String) session.getAttribute("userName");
		int deleteResult = userServiceImpl.deleteContent(userName, id);
		if(deleteResult == UserServiceImpl.DELETE_SUCCESS) {
			map.addAttribute("message", UserServiceImpl.DELETE_SUCCESS_MESSAGE);
			map.addAttribute("result", true);
			map.addAttribute("code", 200);
		}
		else if(deleteResult == UserServiceImpl.DELETE_NOT_EXIST) {
			map.addAttribute("message", UserServiceImpl.DELETE_NOT_EXIST_MESSAGE);
			map.addAttribute("result", false);
			map.addAttribute("code", 400);
		}
		else if(deleteResult == UserServiceImpl.DELETE_NO_ACCESS) {
			map.addAttribute("message", UserServiceImpl.DELETE_NO_ACCESS_MESSAGE);
			map.addAttribute("result", false);
			map.addAttribute("code", 400);
		}
		return map;
	}
	
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap buy(@RequestParam int id, 
			 HttpServletResponse response,
			 HttpSession session) {
		ModelMap map = new ModelMap();
		String userName = (String) session.getAttribute("userName");
		int buyResult = userServiceImpl.buyContent(userName, id);
		if(buyResult == UserServiceImpl.BUY_SUCCESS) {
			map.addAttribute("message", UserServiceImpl.BUY_SUCCESS_MESSAGE);
			map.addAttribute("result", true);
			map.addAttribute("code", 200);
		}
		else if(buyResult == UserServiceImpl.BUY_NOT_EXIST) {
			map.addAttribute("message", UserServiceImpl.BUY_NOT_EXIST_MESSAGE);
			map.addAttribute("result", false);
			map.addAttribute("code", 400);
		}
		else if(buyResult == UserServiceImpl.BUY_ISSELL) {
			map.addAttribute("message", UserServiceImpl.BUY_ISSELL_MESSAGE);
			map.addAttribute("result", false);
			map.addAttribute("code", 400);
		}
		return map;
	}
	
}