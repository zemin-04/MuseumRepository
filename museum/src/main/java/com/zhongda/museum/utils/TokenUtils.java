package com.zhongda.museum.utils;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.impl.TextCodec;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongda.museum.exception.NoTokenException;

/**
 * Title : TokenUtils管理
 * Description : Token工具类
 * @Author dengzm
 */
public class TokenUtils {
	
	private static final Logger logger = Logger.getLogger(TokenUtils.class);
	
	private static ObjectMapper objectMapper = SpringUtils.getBean(ObjectMapper.class);
	
	//用于验证使用：Header中的名字
    public static final String DEFAULT_TOKEN_NAME = "Authorization";
    
    //约定以什么信息开头
    public static final String TOKEN_HEADER_PREFIX = "Bearer";
    
	/** 过期时间标志 */
	public static final String CLAIM_KEY_EXPIRATiON = "exp";
	
	/**
	 * 获取当前请求的token字符串
	 * @return 返回token字符串
	 */
	public static String getToken(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return getTokenFromRequest(request);
	}
	/**
	 * 获取当前请求的token字符串
	 * @return 返回token字符串
	 */
	public static String getTokenFromUrl(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return getTokenFromRequestUrl(request);
	}
	
	/**
	 * 获取请求头中的token字符串
	 * @param request 请求
	 * @return 返回token字符串
	 */
	public static String getTokenFromRequest(HttpServletRequest request){
		String authorization = request.getHeader(DEFAULT_TOKEN_NAME);
		if (null != authorization && authorization.startsWith(TOKEN_HEADER_PREFIX)){
			//截取token得到jwt格式的token信息
			return authorization.substring(TOKEN_HEADER_PREFIX.length() + 1);
		}
		return null;
	}
	
	/**
	 * 获取请求Url中的token字符串
	 * @param request 请求
	 * @return 返回token字符串
	 */
	public static String getTokenFromRequestUrl(HttpServletRequest request){
		String authorization = request.getParameter(DEFAULT_TOKEN_NAME);
		if (null != authorization && authorization.startsWith(TOKEN_HEADER_PREFIX)){
			//截取token得到jwt格式的token信息
			return authorization.substring(TOKEN_HEADER_PREFIX.length() + 1);
		}
		return null;
	}
	
	/**
	 * 解析token，返回token的payload部分
	 * @param token
	 * @return
	 */
	private static String parseTokenBody(String token){
		if(null == token){
			throw new NoTokenException("没有token令牌,验证失败...");
		}else{
			String[] tokenArray = token.split("\\.");
			if(tokenArray.length != 3){
				throw new MalformedJwtException("token令牌格式错误,验证失败...");
			}else{
				return TextCodec.BASE64URL.decodeToString(tokenArray[1]);
			}
		}
	}
	
	/**
	 * 获取token中荷载信息
	 * @param token 传入的token
	 * @return Claims Map集合
	 */
    public static Map<String, Object> getClaimsFromeToken(String token){
    	String tokenBody = parseTokenBody(token);
    	JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, Object.class);
    	try {
			return objectMapper.readValue(tokenBody, javaType);
		} catch (IOException e) {
			logger.error("解析token的荷载部分失败,解码后的json字符串不能转换成Map集合" + e.getMessage());
			return null;
		}
	}
    
    /**
	 * 获取当前token中荷载信息
	 * @return Claims Map集合
	 */
    public static Map<String, Object> getClaims(){
    	return getClaimsFromeToken(getToken());
	}
}
