package com.zhongda.museum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhongda.museum.model.Culturalrelics;

@Mapper
public interface CulturalrelicsMapper {
	int deleteByPrimaryKey(Integer culturalrelicsId);

	int insert(Culturalrelics record);

	int insertSelective(Culturalrelics record);

	Culturalrelics selectByPrimaryKey(Integer culturalrelicsId);

	int updateByPrimaryKeySelective(Culturalrelics record);

	int updateByPrimaryKey(Culturalrelics record);

	/**
	 * 通过ID查询文物
	 * 
	 * @param culturalrelicsId
	 * @return
	 */
	Culturalrelics selectculturalrelicsBuId(
			@Param(value = "culturalrelicsId") Integer culturalrelicsId);
}