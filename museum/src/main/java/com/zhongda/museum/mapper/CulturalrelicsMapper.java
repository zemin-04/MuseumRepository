package com.zhongda.museum.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhongda.museum.model.Culturalrelics;

@Mapper
public interface CulturalrelicsMapper {
	int deleteByPrimaryKey(Integer culturalrelicsId);

	int insert(Culturalrelics record);

	int insertSelective(Culturalrelics record);
	
	/**
	 * 通过ID查询文物
	 * @param culturalrelicsId 文物id
	 * @return
	 */
	Culturalrelics selectByPrimaryKey(Integer culturalrelicsId);

	int updateByPrimaryKeySelective(Culturalrelics record);

	int updateByPrimaryKey(Culturalrelics record);
	
	/**
	 * 根据文物id增加一次对应的文物访问量
	 * @param culturalrelicsId
	 */
	int updateAccessByCulturalrelicsId(Integer culturalrelicsId);
	
	/**
	 * 根据文物id增加一次对应的文物点赞量
	 * @param culturalrelicsId
	 */
	int updatePlusThumbUpByCulturalrelicsId(Integer culturalrelicsId);
	
	/**
	 * 根据文物id减少一次对应的文物点赞量
	 * @param culturalrelicsId
	 */
	int updateMinusThumbUpByCulturalrelicsId(Integer culturalrelicsId);
}