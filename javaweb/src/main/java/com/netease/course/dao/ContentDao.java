package com.netease.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.netease.course.meta.Content;

@Repository 
public interface ContentDao {
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "price", column = "price"),
		@Result(property = "title", column = "title"),
		@Result(property = "image", column = "image"),
		@Result(property = "summary", column = "summary"),
		@Result(property = "detail", column = "detail"),
		@Result(property = "sellerId", column = "sellerId"),
	})
	@Select("select * from content where id = #{id}")
	public Content getContentById(@Param("id") int id);
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "price", column = "price"),
		@Result(property = "title", column = "title"),
		@Result(property = "image", column = "image"),
		@Result(property = "summary", column = "summary"),
		@Result(property = "detail", column = "detail"),
		@Result(property = "sellerId", column = "sellerId"),
	})
	@Select("select * from content")
	public List<Content> getContentList();
	
	@Insert("insert into content(price, title, image, summary, detail, sellerId) "
			+ "values(#{price}, #{title}, #{image}, #{summary}, #{detail}, #{sellerId})")
	public int insert(@Param("title") String title, @Param("price") int price, 
			@Param("image") String image, @Param("summary") String summary, 
			@Param("detail") String detail, @Param("sellerId") int sellerId);
	
	@Delete("delete from content where id = #{id}")
	public int delete(@Param("id") int id);
	
	@Update("update content set title = #{title}, summary = #{summary}, image = #{image}, "
			+ "detail = #{detail}, price = #{price} where id = #{id}")
	public int update(@Param("id") int id, @Param("title") String title, 
			@Param("summary") String summary, @Param("image") String image,
			@Param("detail") String detail, @Param("price") int price);
	
	@Select("select @@identity")
	public int getInsertedId();
	
}
