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
	 * 如果数据库没有当前用户文物的点赞记录，则增加对应用户对文物的点赞
	 * 如果数据库有当前用户文物的点赞记录，则删除对应用户对文物的点赞
	 * @param openid 用户唯一标识
	 * @param culturalrelicsId 文物唯一标识
	 * @return 返回true代表点赞成功， 返回false代表取消点赞成功
	 */
	boolean plusOrMinusThumbUp(String openid, Integer culturalrelicsId);

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
