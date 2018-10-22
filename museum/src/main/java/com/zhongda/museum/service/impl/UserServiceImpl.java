package com.zhongda.museum.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhongda.museum.mapper.UserMapper;
import com.zhongda.museum.model.User;
import com.zhongda.museum.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public boolean insertUser(User user) {
		int index = userMapper.insertSelective(user);
		return index > 0 ? true : false;
	}

	@Override
	public User selectByOpenid(String openid) {
		return userMapper.selectByOpenid(openid);
	}

	@Override
	public List<User> selectAllUser() {
		return userMapper.selectAllUser();
	}

	@Override
	public User findUserById(Integer userid) {
		return userMapper.selectByPrimaryKey(userid);
	}

}
