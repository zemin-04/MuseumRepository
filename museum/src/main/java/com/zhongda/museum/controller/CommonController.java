package com.zhongda.museum.controller;

import java.io.IOException;
import java.util.Arrays;

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

@Controller
public class CommonController {

	@Resource
	private UserService userService;

	@RequestMapping("/home")
	public String home(String code) throws JsonParseException,
			JsonMappingException, IOException {
		System.out.println("code:" + code);
		String url = String
				.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
						WeiXinConfigConstant.APP_ID,
						WeiXinConfigConstant.APP_SECRET, code);
		String result = HttpClientUtil.httpGetRequest(url);
		System.out.println(result);
		ObjectMapper objectMapper = new ObjectMapper();
		OauthToken oauthToken = objectMapper
				.readValue(result, OauthToken.class);
		User user = userService.selectByOpenid(oauthToken.getOpenid());
		if (null == user) {
			String url2 = String
					.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN",
							oauthToken.getAccessToken(), oauthToken.getOpenid());
			String result2 = HttpClientUtil.httpGetRequest(url2);
			System.out.println(result2);
			user = objectMapper.readValue(result2, User.class);
			userService.insertUser(user);
		}
		System.out.println(user.getNickname() + "'"
				+ Arrays.toString(user.getPrivilege()));
		return "redirect:http://zjjlmp.vicp.cc:13025/";
	}
}
