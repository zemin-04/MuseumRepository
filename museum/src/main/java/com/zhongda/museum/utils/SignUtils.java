package com.zhongda.museum.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.zhongda.museum.constant.WeiXinConfigConstant;

public class SignUtils {
	
	/**
	 * 构造签名字符串的模板
	 */
	private static final String SIGN_STR_TEMPLATE = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
	
	/**
	 * 将对应字符串组合生成的签名和微信加密签名对比是否一样
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce  随机数
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
		String tmpStr = signStr(content.toString());
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	
	/**
	 * 生成签名信息并返回
	 * @param jsapiTicket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String jsapiTicket, String url) {
		Map<String, String> result = new HashMap<String, String>();
		String nonceStr = getNonceStr();
		String timestamp = getTimestamp();
		String content = String.format(SIGN_STR_TEMPLATE, jsapiTicket, nonceStr, timestamp, url);
		String signature = signStr(content);
		result.put("url", url);
		result.put("jsapi_ticket", jsapiTicket);
		result.put("nonceStr", nonceStr);
		result.put("timestamp", timestamp);
		result.put("signature", signature);
		return result;
	}
	
	/**
	 * 对传入的字符串进行签名
	 * @param content 需签名的字符串
	 * @return 返回签名的结果
	 */
	private static String signStr(String content) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 将个字符串进行sha1加密
			byte[] digest = md.digest(content.getBytes());
			content = CodeUtils.byteToHex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 根据UUID获取随机数
	 * @return
	 */
	private static String getNonceStr() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取当前时间的时间戳
	 * @return
	 */
	private static String getTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
