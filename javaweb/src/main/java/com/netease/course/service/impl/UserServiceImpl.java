package com.netease.course.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.course.dao.ContentDao;
import com.netease.course.dao.TransactionDao;
import com.netease.course.dao.UserDao;
import com.netease.course.meta.BuyList;
import com.netease.course.meta.Content;
import com.netease.course.meta.TransactionRecord;
import com.netease.course.meta.User;

@Service
public class UserServiceImpl {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private TransactionDao transactionDao;
	
	public static final int BUY_SUCCESS = 0;
	public static final int BUY_NOT_EXIST = 1;
	public static final int BUY_ISSELL = 2;
	
	public static final String BUY_SUCCESS_MESSAGE = "购买成功";
	public static final String BUY_NOT_EXIST_MESSAGE = "内容不存在";
	public static final String BUY_ISSELL_MESSAGE = "库存不足";
	
	public static final int DELETE_SUCCESS = 0;
	public static final int DELETE_NOT_EXIST = 1;
	public static final int DELETE_NO_ACCESS = 2;
	
	public static final String DELETE_SUCCESS_MESSAGE = "删除成功";
	public static final String DELETE_NOT_EXIST_MESSAGE = "内容不存在";
	public static final String DELETE_NO_ACCESS_MESSAGE = "无权限访问";
	
	@Transactional
	public int buyContent(String userName, int contentId) {
		int buyerId = userDao.getUserByName(userName).getId();
		int sellerId = contentDao.getContentById(contentId).getSellerId();
		int buyPrice = contentDao.getContentById(contentId).getPrice();
		if(transactionDao.getTransactionRecordByContentId(contentId) != null)
			return BUY_ISSELL;
		int insertTransactionRecordResult = transactionDao.insert(buyerId, contentId, buyPrice, 0);
		return BUY_SUCCESS;
	}
	
	@Transactional
	public int deleteContent(String userName, int contentId) {
		int userId = userDao.getUserByName(userName).getId();
		if(transactionDao.getTransactionRecordByContentId(contentId) != null)
			return DELETE_NO_ACCESS;
		if(userId != contentDao.getContentById(contentId).getSellerId())
			return DELETE_NO_ACCESS;
		int result = contentDao.delete(contentId);
		if(result == 0)
			return DELETE_NOT_EXIST;
		return DELETE_SUCCESS;
	}
	
	@Transactional
	public int publicSubmit(String title, int price, String image, String summary, String detail, String sellerName) {
		int sellerId = userDao.getUserByName(sellerName).getId();
		contentDao.insert(title, price, image, summary, detail, sellerId);
		int id = contentDao.getInsertedId();
		return id;
	}
	
	public Content getContent(String userName, int id) {
		Content content = contentDao.getContentById(id);
		if(content != null && content.getSellerId() != userDao.getUserByName(userName).getId())
			return null;
		return content;
	}

	public boolean saveEdit(Content unsaved) {
		Content content = contentDao.getContentById(unsaved.getId());
		if(content.getSellerId() != unsaved.getSellerId())
			return false;
		int affected = contentDao.update(unsaved.getId(), unsaved.getTitle(), unsaved.getSummary(),
				unsaved.getImage(), unsaved.getDetail(), unsaved.getPrice());
		if(affected == 0)
			return false;
		else
			return true;
	}
	
	public User getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}

	public List<BuyList> getBuyList(int userId) {
		List<BuyList> buyListArray = new ArrayList<BuyList>();
		List<TransactionRecord> transactionRecordList = transactionDao.getTransactionRecordList();
		for(TransactionRecord transactionRecord: transactionRecordList) {
			BuyList buyList = new BuyList();
			Content content = contentDao.getContentById(transactionRecord.getContentId());
			buyList.setId(transactionRecord.getContentId());
			buyList.setImage(content.getImage());
			buyList.setTitle(content.getTitle());
			buyList.setBuyPrice(transactionRecord.getBuyPrice());
			buyList.setBuyTime(transactionRecord.getBuyTime());
			buyListArray.add(buyList);
		}
		return buyListArray;
	}
	
}
