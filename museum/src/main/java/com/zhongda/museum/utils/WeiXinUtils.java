package com.zhongda.museum.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongda.museum.constant.WeiXinConfigConstant;
import com.zhongda.museum.model.AccessToken;
import com.zhongda.museum.model.JsapiTicket;
import com.zhongda.museum.model.OauthToken;
import com.zhongda.museum.model.User;
import com.zhongda.museum.model.menu.Menu;

public class WeiXinUtils {

	private static Logger logger = LoggerFactory.getLogger(WeiXinUtils.class);

	private static ObjectMapper objectMapper = SpringUtils.getBean(ObjectMapper.class);

	/**
	 * 获取微信access_token
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = (AccessToken) CacheUtils.get(CacheUtils.CACHE_ACCESS_TOKEN, "accessToken");
		if(null == accessToken){
			String url = String.format(WeiXinConfigConstant.GET_ACCESS_TOKEN_URL,
					WeiXinConfigConstant.APP_ID, WeiXinConfigConstant.APP_SECRET);
			String result = HttpClientUtils.httpGetRequest(url);
			try {
				accessToken = objectMapper.readValue(result, AccessToken.class);
				CacheUtils.put(CacheUtils.CACHE_ACCESS_TOKEN, "accessToken", accessToken);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return accessToken;
	}
	
	/**
	 * 获取微信JS API 的Ticket
	 * @param signUrl
	 * @return
	 */
	public static JsapiTicket getJsapiTicket() {
		JsapiTicket jsapiTicket = (JsapiTicket) CacheUtils.get(CacheUtils.CACHE_JSAPI_TICKET, "jsapiTicket");
		if(null == jsapiTicket){
			String url = String.format(WeiXinConfigConstant.GET_JSAPI_TICKET_URL, getAccessToken().getAccessToken());
			String result = HttpClientUtils.httpGetRequest(url);
			try {
				jsapiTicket = objectMapper.readValue(result, JsapiTicket.class);
				CacheUtils.put(CacheUtils.CACHE_JSAPI_TICKET, "jsapiTicket", jsapiTicket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsapiTicket;
	}

	/**
	 * 自定义菜单
	 * @param menu 菜单对象
	 */
	public static void createMenu(Menu menu) {
		String url = String.format(WeiXinConfigConstant.CREATE_MENU_URL, getAccessToken().getAccessToken());
		String jsonData = null;
		try {
			jsonData = objectMapper.writeValueAsString(menu);
			String result = HttpClientUtils.httpPostRequest(url, jsonData);
			logger.info(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取微信oauth_token
	 * @param code
	 * @return
	 */
	public static OauthToken getOauthToken(String code) {
		String url = String.format(WeiXinConfigConstant.GET_OAUTH_TOKEN_URL,
				WeiXinConfigConstant.APP_ID, WeiXinConfigConstant.APP_SECRET,
				code);
		String result = HttpClientUtils.httpGetRequest(url);
		OauthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(result, OauthToken.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return oauthToken;
	}

	/**
	 * 获取微信用户信息
	 * 
	 * @param oauthToken
	 * @return
	 */
	public static User getUserInfo(OauthToken oauthToken) {
		String url = String.format(WeiXinConfigConstant.GET_USER_INFO_URL,
				oauthToken.getAccessToken(), oauthToken.getOpenid());
		String result = HttpClientUtils.httpGetRequest(url);
		User user = null;
		try {
			user = objectMapper.readValue(result, User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}
}