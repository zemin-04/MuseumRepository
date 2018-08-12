package com.zhongda.museum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhongda.museum.model.ThumbUp;

@Mapper
public interface ThumbUpMapper {
    int deleteByPrimaryKey(Integer thumbUpId);

    int insert(ThumbUp record);

    int insertSelective(ThumbUp record);

    ThumbUp selectByPrimaryKey(Integer thumbUpId);

    int updateByPrimaryKeySelective(ThumbUp record);

    int updateByPrimaryKey(ThumbUp record);
	
	/**
	 * 根据用户id和文物id查询点赞记录
	 * @param openid 用户唯一标识
	 * @param culturalrelicsId 文物唯一标识
	 * @return
	 */
	ThumbUp selectByOpenidAndCulturalrelicsId(@Param(value = "openid") String openid, 
			@Param(value = "culturalrelicsId") Integer culturalrelicsId);
}