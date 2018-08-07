package com.zhongda.museum.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhongda.museum.model.Culturalrelics;

@Mapper
public interface CulturalrelicsMapper {
	int deleteByPrimaryKey(Integer culturalrelicsId);

	int insert(Culturalrelics record);

	int insertSelective(Culturalrelics record);

	Culturalrelics selectByPrimaryKey(Integer culturalrelicsId);

	int updateByPrimaryKeySelective(Culturalrelics record);

	int updateByPrimaryKey(Culturalrelics record);
}