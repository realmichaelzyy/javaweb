package com.netease.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.netease.course.meta.TransactionRecord;

@Repository 
public interface TransactionDao {
	
	@Insert("insert into transaction_record(userId, contentId, buyPrice, buyTime) "
			+ "values(#{userId}, #{contentId}, #{buyPrice}, #{buyTime})")
	public int insert(@Param("userId") int userId,  @Param("contentId") int contentId, 
			@Param("buyPrice") int buyPrice, @Param("buyTime") int buyTime);
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "userId", column = "userId"),
		@Result(property = "contentId", column = "contentId"),
		@Result(property = "buyPrice", column = "buyPrice"),
		@Result(property = "buyTime", column = "buyTime"),
	})
	@Select("select * from transaction_record")
	public List<TransactionRecord> getTransactionRecordList();
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "userId", column = "userId"),
		@Result(property = "contentId", column = "contentId"),
		@Result(property = "buyPrice", column = "buyPrice"),
		@Result(property = "buyTime", column = "buyTime"),
	})
	@Select("select * from transaction_record where contentId = #{contentId}")
	public TransactionRecord getTransactionRecordByContentId(@Param("contentId") int contentId);
	
}
