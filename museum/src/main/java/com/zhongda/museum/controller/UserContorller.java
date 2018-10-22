package com.zhongda.museum.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.model.User;
import com.zhongda.museum.service.UserService;

@RestController
@RequestMapping("/user")
@Api(tags = { "用户操作接口" })
public class UserContorller {

	@Resource
	private UserService userService;

	@GetMapping("/findAllUser")
	@ApiOperation(value = "查询所有用户", httpMethod = "GET", response = List.class, notes = "查询所有用户")
	public List<User> findAllUser() {
		return userService.selectAllUser();
	}

	@PostMapping("/findUserById")
	@ApiOperation(value = "根据用户id查找用户", httpMethod = "POST", response = User.class, notes = "根据用户id查找用户")
	public User findUserById(Integer userId) {
		return userService.findUserById(userId);
	}
}
