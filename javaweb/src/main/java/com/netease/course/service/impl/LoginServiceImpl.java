package com.netease.course.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.course.dao.UserDao;
import com.netease.course.meta.User;

@Service
public class LoginServiceImpl {
	
	public static final int LOGIN_SUCCESS = 0;
	public static final int LOGIN_USERNAME_NOT_EXIST = 1;
	public static final int LOGIN_PASSWORD_ERROR = 2;
	
	public static final String LOGIN_SUCCESS_MESSAGE = "登录成功";
	public static final String LOGIN_USERNAME_NOT_EXIST_MESSAGE = "用户名不存在";
	public static final String LOGIN_PASSWORD_ERROR_MESSAGE = "密码错误";
	
	public static final int USER_NOT_LOGIN = 0;
	public static final int USER_BUYER = 1;
	public static final int USER_SELLER = 2;

	@Autowired
	private UserDao userDao;
	
	public int login(String userName, String password) {
		User user = userDao.getUserByName(userName);
		if(user == null)
			return LOGIN_USERNAME_NOT_EXIST;
		else if(password.equals(user.getPassword()))
			return LOGIN_SUCCESS;
		else
			return LOGIN_PASSWORD_ERROR;
	}

}
