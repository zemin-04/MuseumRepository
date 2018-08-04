package com.zhongda.museum.service;

import com.zhongda.museum.model.User;

public interface UserService {
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	boolean insertUser(User user);
	
	/**
	 * 根据openid查找用户
	 * @param openid 用户唯一标识
	 * @return
	 */
	User selectByOpenid(String openid);
}
