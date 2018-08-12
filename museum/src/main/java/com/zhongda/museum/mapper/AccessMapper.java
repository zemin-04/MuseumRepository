package com.zhongda.museum.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhongda.museum.model.Access;

@Mapper
public interface AccessMapper {
    int deleteByPrimaryKey(Integer accessId);

    int insert(Access record);

    int insertSelective(Access record);

    Access selectByPrimaryKey(Integer accessId);

    int updateByPrimaryKeySelective(Access record);

    int updateByPrimaryKey(Access record);
}