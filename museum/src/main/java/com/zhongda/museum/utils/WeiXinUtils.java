package com.zhongda.museum.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

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

	private static ObjectMapper objectMapper = SpringUtils
			.getBean(ObjectMapper.class);
	// 与接口配置信息中的Token要一致
	public static AccessToken accessToken = null;

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] array = new String[] { WeiXinConfigConstant.TOKEN, timestamp,
				nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(array);
		StringBuilder content = new StringBuilder();
		for (String str : array) {
			content.append(str);
		}
		String tmpStr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = CodeUtils.byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 获取微信access_token
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken() {
		String url = String.format(WeiXinConfigConstant.GET_ACCESS_TOKEN_URL,
				WeiXinConfigConstant.APP_ID, WeiXinConfigConstant.APP_SECRET);
		String result = HttpClientUtils.httpGetRequest(url);
		logger.info(result);
		AccessToken token = null;
		try {
			token = objectMapper.readValue(result, AccessToken.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * 自定义菜单
	 * 
	 * @param menu
	 *            菜单对象
	 */
	public static void createMenu(Menu menu) {
		if (null == accessToken) {
			accessToken = getAccessToken();
		}
		String url = String.format(WeiXinConfigConstant.CREATE_MENU_URL,
				accessToken.getAccessToken());
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
	 * 
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

	public static Map<String, String> getJsapiTicket(String signUrl) {
		String url = String.format(WeiXinConfigConstant.GRT_JSAPI_TICKET,
				accessToken.getAccessToken());
		System.out.println("url:" + url);
		String result = HttpClientUtils.httpGetRequest(url);
		System.out.println("jsapi_ticket：" + result);
		JsapiTicket jsapiTicket = null;
		Map<String, String> sign = null;
		try {
			jsapiTicket = objectMapper.readValue(result, JsapiTicket.class);
			sign = Sign.sign(jsapiTicket.getTicket(), signUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sign;
	}
}