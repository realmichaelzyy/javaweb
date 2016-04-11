package com.netease.course.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netease.course.dao.UserDao;
import com.netease.course.meta.BuyList;
import com.netease.course.meta.Content;
import com.netease.course.meta.Product;
import com.netease.course.service.impl.LoginServiceImpl;
import com.netease.course.service.impl.ShowProductServiceImpl;
import com.netease.course.service.impl.UserServiceImpl;

@Controller
public class MVCController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginServiceImpl loginServiceImpl;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ShowProductServiceImpl showProductServiceImpl;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(@RequestParam(required = false) Integer type,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("index");
		String userName = (String) session.getAttribute("userName");
		List<Product> productList = showProductServiceImpl.getProductList(userName);
	    modelAndView.addObject("productList", productList);
	    return modelAndView;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletResponse response,
			HttpSession session) throws IOException {
		String userName = (String) session.getAttribute("userName");
		if(userName != null)
			session.removeAttribute("userName");
		response.sendRedirect("/");
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int id,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("show");
		String userName = (String) session.getAttribute("userName");
		Product product = showProductServiceImpl.getProduct(userName, id);
		modelAndView.addObject("product", product);
		return modelAndView;
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView account(HttpServletResponse response,
			HttpSession session) throws IOException {
		ModelAndView modelAndView = new ModelAndView("account");
		String userName = (String) session.getAttribute("userName");
		int userId = userServiceImpl.getUserByName(userName).getId();
		List<BuyList> buyList = userServiceImpl.getBuyList(userId);
		if(buyList != null) {
			modelAndView.addObject("buyList", buyList);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/public", method = RequestMethod.GET)
	public ModelAndView publish(HttpServletResponse response,
			HttpSession session) throws IOException {
		ModelAndView modelAndView = new ModelAndView("public");
		String userName = (String) session.getAttribute("userName");
		int usertype = userServiceImpl.getUserByName(userName).getUserType();
		if(usertype == 0) {
			modelAndView.setViewName("error");
			return modelAndView;
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/publicSubmit", method = RequestMethod.POST)
	public ModelAndView publicSubmit(@RequestParam String title,
			@RequestParam String image,
			@RequestParam int price,
			@RequestParam String summary,
			@RequestParam String detail,
			HttpServletResponse response,
			HttpSession session) throws IOException {
		ModelAndView modelAndView = new ModelAndView("publicSubmit");
		String userName = (String) session.getAttribute("userName");
		int usertype = userServiceImpl.getUserByName(userName).getUserType();
		if(usertype == 0) {
			modelAndView.setViewName("error");
			return modelAndView;
		}
		int id = userServiceImpl.publicSubmit(title, price, image, summary, detail, userName);
		Product product = showProductServiceImpl.getProduct(userName, id);
		if(product != null) 
			modelAndView.addObject("product", product);
		return modelAndView;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int id,
			HttpServletResponse response,
			HttpSession session) throws IOException {
		ModelAndView modelAndView = new ModelAndView("edit");
		String userName = (String) session.getAttribute("userName");
		int usertype = userServiceImpl.getUserByName(userName).getUserType();
		if(usertype == 0) {
			modelAndView.setViewName("error");
			return modelAndView;
		}
		Content content = userServiceImpl.getContent(userName, id);
		if(content == null) {
			modelAndView.setViewName("error");
			return modelAndView;
		}
		modelAndView.addObject("product", content);
		return modelAndView;
	}
	
	@RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
	public ModelAndView editSubmit(@RequestParam int id,
			@RequestParam String title,
			@RequestParam String image,
			@RequestParam int price,
			@RequestParam String summary,
			@RequestParam String detail,
			HttpServletResponse response,
			HttpSession session) throws IOException {
		ModelAndView modelAndView = new ModelAndView("editSubmit");
		String userName = (String) session.getAttribute("userName");
		int usertype = userServiceImpl.getUserByName(userName).getUserType();
		if(usertype == 0) {
			modelAndView.setViewName("error");
			return modelAndView;
		}
		int sellerId = userServiceImpl.getUserByName(userName).getId();
		Content content = new Content(id, title, summary, image, detail, price, sellerId);
		boolean saveStatus = userServiceImpl.saveEdit(content);
		if(saveStatus == true)
			modelAndView.addObject("product", content);
		return modelAndView;
	}
	
}
