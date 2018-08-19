package com.zhongda.museum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class ManageController {

	private static Logger logger = LoggerFactory.getLogger(ManageController.class);

	@RequestMapping("/access")
	public String access() {
		logger.info("进入后台管理访问量统计页面");
		return "index";
	}
}
