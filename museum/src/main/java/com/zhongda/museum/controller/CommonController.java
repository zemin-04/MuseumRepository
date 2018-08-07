package com.zhongda.museum.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongda.museum.constant.WeiXinConfigConstant;
import com.zhongda.museum.model.OauthToken;
import com.zhongda.museum.model.User;
import com.zhongda.museum.service.UserService;
import com.zhongda.museum.utils.HttpClientUtil;
import com.zhongda.museum.utils.JwtTokenUtils;

@Controller
public class CommonController {

	@Resource
	private UserService userService;
	
	@Resource
	private ObjectMapper objectMapper;

	@RequestMapping("/home")
	public String home(String code) throws JsonParseException,
			JsonMappingException, IOException {
		String url = String
				.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
						WeiXinConfigConstant.APP_ID,
						WeiXinConfigConstant.APP_SECRET, code);
		String result = HttpClientUtil.httpGetRequest(url);
		OauthToken oauthToken = objectMapper.readValue(result, OauthToken.class);
		User user = userService.selectByOpenid(oauthToken.getOpenid());
		if (null == user) {
			String url2 = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN",
							oauthToken.getAccessToken(), oauthToken.getOpenid());
			String userStr = HttpClientUtil.httpGetRequest(url2);
			user = objectMapper.readValue(userStr, User.class);
			userService.insertUser(user);
		}
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("openid", user.getOpenid());
		claims.put("userName", user.getNickname());
		String token = JwtTokenUtils.createJsonWebToken(claims);
		return "redirect:http://zjjlmp.vicp.cc:13025/";
	}
}
