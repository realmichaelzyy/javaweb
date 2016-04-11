package com.netease.course.meta;

public class TransactionRecord {
	
	private int id;
	private int userId;
	private int contentId;
	private int buyPrice;
	private int buyTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public int getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(int buyTime) {
		this.buyTime = buyTime;
	}
	
}
