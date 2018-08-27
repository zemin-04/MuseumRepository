package com.zhongda.museum.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhongda.museum.model.User;
import com.zhongda.museum.service.UserService;

@Controller
@RequestMapping("/manage")
public class ManageController {

	private static Logger logger = LoggerFactory.getLogger(ManageController.class);
	
	@Resource
	private UserService userService;

	@RequestMapping("/index")
	public String access(Model model) {
		logger.info("进入后台管理访问量统计页面");
		List<User> userList = userService.selectAllUser();
		model.addAttribute(userList);
		return "index";
	}
}
