package com.zhongda.museum.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.model.Culturalrelics;
import com.zhongda.museum.model.Result;
import com.zhongda.museum.model.User;
import com.zhongda.museum.service.CulturalrelicsService;
import com.zhongda.museum.utils.ShiroUtils;

@RestController
@RequestMapping("/culturalrelics")
@Api(tags = { "文物操作接口" })
public class CulturalrelicsController {
	
	@Resource
	private CulturalrelicsService culturalrelicsService;

	@PostMapping("/plusAccess")
	@ApiOperation(value = "对应文物增加一次访问量", httpMethod = "POST", response = Integer.class, notes = "根据文物id对应文物增加一次访问量")
	@ApiImplicitParams({ @ApiImplicitParam(name = "culturalrelicsId", value = "文物id", required = true, dataType = "Integer", paramType = "query") })
	public Result<String> plusAccess(Integer culturalrelicsId) {
		Result<String> result = new Result<String>();
		User user = ShiroUtils.getCurrentUser();
		boolean flag = culturalrelicsService.plusAccess(user.getOpenid(), culturalrelicsId);
		return flag ? result.success("增加访问量成功") : result.failure("增加访问量失败");
	}
	
	@PostMapping("/plusThumbUp")
	@ApiOperation(value = "对应文物增加一次点赞量", httpMethod = "POST", response = Void.class, notes = "根据文物id对应文物增加一次点赞量")
	@ApiImplicitParams({ @ApiImplicitParam(name = "culturalrelicsId", value = "文物id", required = true, dataType = "Integer", paramType = "query") })
	public Result<String> plusThumbUp(Integer culturalrelicsId) {
		Result<String> result = new Result<String>();
		User user = ShiroUtils.getCurrentUser();
		boolean flag = culturalrelicsService.plusThumbUp(user.getOpenid(), culturalrelicsId);
		return flag ? result.success("点赞成功") : result.failure("已经点赞，请不要重复点赞");
	}
	
	@PostMapping("/minusThumbUp")
	@ApiOperation(value = "对应文物减少一次点赞量", httpMethod = "POST", response = Void.class, notes = "根据文物id对应文物减少一次点赞量")
	@ApiImplicitParams({ @ApiImplicitParam(name = "culturalrelicsId", value = "文物id", required = true, dataType = "Integer", paramType = "query") })
	public Result<String> minusThumbUp(Integer culturalrelicsId) {
		Result<String> result = new Result<String>();
		User user = ShiroUtils.getCurrentUser();
		boolean flag = culturalrelicsService.minusThumbUp(user.getOpenid(), culturalrelicsId);
		return flag ? result.success("取消点赞成功") : result.failure("还没有点赞，请先点赞");
	}
	
	@GetMapping("/findCulturalrelics")
	@ApiOperation(value = "通过文物id查询文物", httpMethod = "GET", response = Culturalrelics.class, notes = "通过文物id查询文物")
	@ApiImplicitParams({ @ApiImplicitParam(name = "culturalrelicsId", value = "文物id", required = true, dataType = "Integer", paramType = "query") })
	public Culturalrelics findCulturalrelics(Integer culturalrelicsId) {
		return culturalrelicsService.findCulturalrelics(culturalrelicsId);
	}
}
