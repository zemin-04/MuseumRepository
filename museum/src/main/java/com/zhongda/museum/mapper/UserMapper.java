package com.zhongda.museum.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhongda.museum.model.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User selectByOpenid(String openid);
}