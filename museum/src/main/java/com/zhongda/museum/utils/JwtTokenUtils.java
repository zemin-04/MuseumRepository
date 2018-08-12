package com.zhongda.museum.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JwtTokenUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

	/** Token加密密钥(本地类中) */
	private static final String JWT_SECRET = "Museum";
	/** Token默认过期时间7天(单位毫秒) */
	private static final int JWT_EXP = 7 * 24 * 60 * 60 * 1000;
	
	/**
	 * 创建一个JsonWebToken
	 * @param claims 添加到token中的信息
	 * @return 返回创建好的token
	 */
	public static String createJsonWebToken(Map<String, Object> claims) {
		long nowMillis = System.currentTimeMillis();
		Date nowDate = new Date(nowMillis);
		SecretKey key = getKey();
		JwtBuilder builder = Jwts.builder().setIssuedAt(nowDate);
		// 使用默认过期时间
		long expMillis = nowMillis + JWT_EXP;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);
		String token = builder.addClaims(claims) //claims中如果存在同名的参数，则会覆盖上面设置的参数
			   .signWith(SignatureAlgorithm.HS256, key)
			   .compact();
		return token;
	}
	
	/**
	 * 获取密钥
	 */
	private static SecretKey getKey() {
		byte[] encodedKey = Base64.getDecoder().decode(JWT_SECRET);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}
	
	public static Claims parseToken(String token) {
		return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
	}

	public static boolean checkToken(String token) {
		try {
			Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
        	logger.error("JWT token已过期..." + e.getMessage());
        } catch (SignatureException e) {
			logger.error("无效的JWT签名..." + e.getMessage());
        } catch (MalformedJwtException e) {
        	logger.error("无效的JWT token..." + e.getMessage());
        } catch (UnsupportedJwtException e) {
        	logger.error("不支持的JWT token..." + e.getMessage());
        } catch (IllegalArgumentException e) {
        	logger.error("JWT token的组成处理无效..." + e.getMessage());
        } catch (Exception e) {
			logger.error("token令牌无效, 验证失败..." + e.getMessage());
		}
		return false;
	}
}
