package com.zhongda.museum.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhongda.museum.model.OauthToken;
import com.zhongda.museum.model.User;
import com.zhongda.museum.service.UserService;
import com.zhongda.museum.utils.JwtTokenUtils;
import com.zhongda.museum.utils.TokenUtils;
import com.zhongda.museum.utils.WeiXinUtils;

@Controller
public class OauthController {

	private static Logger logger = LoggerFactory
			.getLogger(OauthController.class);

	@Resource
	private UserService userService;

	@RequestMapping("/home")
	public String home(String code) {
		OauthToken oauthToken = WeiXinUtils.getOauthToken(code);
		User user = userService.selectByOpenid(oauthToken.getOpenid());
		if (null == user) {
			user = WeiXinUtils.getUserInfo(oauthToken);
			userService.insertUser(user);
		}
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("openid", user.getOpenid());
		claims.put("userName", user.getNickname());
		String jwtToken = JwtTokenUtils.createJsonWebToken(claims);
		logger.info(jwtToken);
		return "redirect:http://zjjlmp.vicp.cc:13460?"
				+ TokenUtils.DEFAULT_TOKEN_NAME + "=" + jwtToken;
	}
}
