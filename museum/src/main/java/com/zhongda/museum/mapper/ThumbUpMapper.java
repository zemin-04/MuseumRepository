package com.zhongda.museum.mapper;

import com.zhongda.museum.model.ThumbUp;

public interface ThumbUpMapper {
    int deleteByPrimaryKey(Integer thumbUpId);

    int insert(ThumbUp record);

    int insertSelective(ThumbUp record);

    ThumbUp selectByPrimaryKey(Integer thumbUpId);

    int updateByPrimaryKeySelective(ThumbUp record);

    int updateByPrimaryKey(ThumbUp record);
}