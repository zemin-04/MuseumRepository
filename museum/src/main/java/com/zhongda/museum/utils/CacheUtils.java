package com.zhongda.museum.utils;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * Title : CacheUtils管理
 * Description : 处理缓存的增删改查操作     缓存javaben,javaben要序列化
 * @Author dengzm   
 * @Date 2018年3月8日 下午3:59:10
 */
public class CacheUtils{

	private static CacheManager cacheManager = (EhCacheCacheManager) SpringUtils.getBean(EhCacheCacheManager.class);

	/*
     *   |- 用户缓存（userCache）
     *       |- 按用户id存储              key: userId- + userId             value:User
     *       |- 按用户名存储 key: key: userName- + userName         value:User
     */
    public static final String CACHE_USER = "userCache";
    public static final String CACHE_ACCESS_TOKEN = "accessTokenCache";
    public static final String CACHE_JSAPI_TICKET = "jsapiTicketCache";
    
	/**
	 * 获取缓存
	 */
	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element == null ? null : element.getObjectValue();
	}

	/**
	 * 写入缓存
	 */
	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	/**
	 * 从缓存中移除
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	/**
	 * 通过缓存名称获取对应的缓存
	 */
	private static Ehcache getCache(String cacheName){
		Ehcache cache = ((EhCacheCacheManager) cacheManager).getCacheManager().getCache(cacheName);
		return cache;
	}
}