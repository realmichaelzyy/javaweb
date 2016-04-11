package com.netease.course.meta;

public class MinUser {
	
	private String username;
	private int usertype;
	
	public MinUser() {}
	public MinUser(String username, int usertype) {
		this.username = username;
		this.usertype = usertype;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	
}
