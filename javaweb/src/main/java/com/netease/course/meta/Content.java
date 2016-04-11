package com.netease.course.meta;

public class Content {
	
	private int id;
	private String title;
	private String image;
	private int price;
	private String summary;
	private String detail;
	private int sellerId;
	
	public Content() {}
	public Content(int id, String title, String summary, String image, String detail, int price, int sellerId) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.image = image;
		this.detail = detail;
		this.price = price;
		this.sellerId = sellerId;
	}
	
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getId() {
		return id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
