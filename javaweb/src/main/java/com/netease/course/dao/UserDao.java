package com.netease.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.netease.course.meta.User;

@Repository 
public interface UserDao {
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "userName", column = "userName"),
		@Result(property = "password", column = "password"),
		@Result(property = "nickName", column = "nickName"),
		@Result(property = "userType", column = "userType"),
	})
	@Select("select * from user where userName = #{userName}")
	public User getUserByName(@Param("userName") String userName);
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "userName", column = "userName"),
		@Result(property = "password", column = "password"),
		@Result(property = "nickName", column = "nickName"),
		@Result(property = "userType", column = "userType"),
	})
	@Select("select * from user")
	public List<User> getUserList();
	
}
