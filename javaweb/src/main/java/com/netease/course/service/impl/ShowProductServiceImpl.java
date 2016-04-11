package com.netease.course.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.course.dao.ContentDao;
import com.netease.course.dao.TransactionDao;
import com.netease.course.dao.UserDao;
import com.netease.course.meta.Content;
import com.netease.course.meta.Product;
import com.netease.course.meta.TransactionRecord;

@Service
public class ShowProductServiceImpl {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private TransactionDao transactionDao;
	
	public Product getProduct(String userName, int id) {
		Content content = contentDao.getContentById(id);
		boolean isBuy = false;
		boolean isSell = false;
		int buyPrice = 0;
		TransactionRecord transactionRecord = transactionDao.getTransactionRecordByContentId(content.getId());
		if(userName != null && transactionRecord != null) {
			if(userDao.getUserByName(userName).getId() == transactionRecord.getUserId()) {
				isBuy = true;
				buyPrice = transactionRecord.getBuyPrice();
			}
			if(userDao.getUserByName(userName).getId() == content.getSellerId())
				isSell = true;
		}
		Product product = new Product();
		product.setId(content.getId());
		product.setTitle(content.getTitle());
		product.setDetail(content.getDetail());
		product.setImage(content.getImage());
		product.setPrice(content.getPrice());
		product.setSummary(content.getSummary());
		product.setSellerId(content.getSellerId());
		
		product.setIsBuy(isBuy);
		product.setIsSell(isSell);
		product.setBuyPrice(buyPrice);
		return product;
	}
	
	public List<Product> getProductList(String userName) {
		List<Content> contentList = contentDao.getContentList();
		List<Product> productList = new ArrayList<Product>();
		for(Content content : contentList) {
			TransactionRecord transactionRecord = transactionDao.getTransactionRecordByContentId(content.getId());
			boolean isBuy = false;
			boolean isSell = false;
			int buyPrice = 0;
			if(userName != null && transactionRecord != null) {
				if(userDao.getUserByName(userName).getId() == transactionRecord.getUserId()) {
					isBuy = true;
					buyPrice = transactionRecord.getBuyPrice();
				}
				if(userDao.getUserByName(userName).getId() == content.getSellerId())
					isSell = true;
			}
			Product product = new Product();
			product.setId(content.getId());
			product.setTitle(content.getTitle());
			product.setDetail(content.getDetail());
			product.setImage(content.getImage());
			product.setPrice(content.getPrice());
			product.setSummary(content.getSummary());
			product.setSellerId(content.getSellerId());
			
			product.setIsBuy(isBuy);
			product.setIsSell(isSell);
			product.setBuyPrice(buyPrice);
			productList.add(product);
		}
		return productList;
	}
	
}
