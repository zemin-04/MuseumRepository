package com.zhongda.museum.service;

import java.util.List;

import com.zhongda.museum.model.Culturalrelics;

public interface CulturalrelicsService {
	
	/**
	 * 根据文物id查询文物详情
	 * @param culturalrelicsId 文物id
	 * @return
	 */
	Culturalrelics findCulturalrelics(Integer culturalrelicsId);
	
	/**
	 * 增加对应用户对文物的一次访问量
	 * @param openid 用户唯一标识
	 * @param culturalrelicsId 文物唯一标识
	 * @return
	 */
	boolean plusAccess(String openid, Integer culturalrelicsId);
	
	/**
	 * 增加对应用户对文物的一次点赞量
	 * @param openid 用户唯一标识
	 * @param culturalrelicsId 文物唯一标识
	 * @return
	 */
	boolean plusThumbUp(String openid, Integer culturalrelicsId);
	
	/**
	 * 减少对应用户对文物的一次点赞量
	 * @param openid 用户唯一标识
	 * @param culturalrelicsId 文物唯一标识
	 * @return
	 */
	boolean minusThumbUp(String openid, Integer culturalrelicsId);

	/**
	 * 搜索符合条件的文物
	 * 
	 * @param condition
	 * @return
	 */
	List<Culturalrelics> selectCulturalrelicsLikecondition(String condition);

	/**
	 * 查询展厅下的文物
	 * 
	 * @param themeId
	 * @return
	 */
	List<Culturalrelics> selectculturalrelicsByThemeId(Integer themeId);

}
