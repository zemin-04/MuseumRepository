package com.zhongda.museum.utils;

import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;

import com.zhongda.museum.exception.NoTokenException;
import com.zhongda.museum.model.User;

/**
 * 类ShiroUtils的功能描述:
 * Shiro工具类
 * @auther dengzm
 */
public class ShiroUtils {
	
	/**  加密算法 */
	public final static String ALGORITHM_NAME = "MD5";
	
	/** 加密散列次数 */
	public static final int HASH_ITERATIONS= 1024;
	
	/**
	 * 加密密码（使用默认的MD5摘要加密，散列值默认为1024）
	 * @param password 需加密的密码
	 * @param salt 盐
	 * @return
	 * 
	 */
	public static String encryptPassword(String password, String salt) {
		return new SimpleHash(ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
	}
	
	/**
	 * 获取当前登录用户（shiro框架 user）
	 * @return
	 */
	public static User getCurrentUser() {
		String token = TokenUtils.getToken();
		return getUserFromToken(token);
	}
	/**
	 * 获取当前登录用户（shiro框架 user）
	 * @return
	 */
	public static User getCurrentUserFromUrl() {
		String token = TokenUtils.getTokenFromUrl();
		return getUserFromToken(token);
	}
	
	/**
	 * 从给定的token中获取当前登录用户（shiro框架 user）
	 * @return
	 */
	public static User getUserFromToken(String token) {
		if(null == token){
			throw new NoTokenException("token失效或用户已退出，请重新登录认证");
		}
		Map<String, Object> claims = TokenUtils.getClaimsFromeToken(token);
		String openid = (String) claims.get("openid");
		String nickname = (String) claims.get("nickname");
		if(null == openid && null == nickname){
			throw new NoTokenException("token无效或已失效，请重新登录认证");
		}
		return new User(openid, nickname);
	}
}
