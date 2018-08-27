package com.zhongda.museum.controller;

import java.util.List;

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
	@ApiOperation(value = "对应文物增加一次访问量", httpMethod = "POST", response = Result.class, notes = "根据文物id对应文物增加一次访问量")
	@ApiImplicitParams({ @ApiImplicitParam(name = "culturalrelicsId", value = "文物id", required = true, dataType = "Integer", paramType = "query") })
	public Result<String> plusAccess(Integer culturalrelicsId) {
		Result<String> result = new Result<String>();
		User user = ShiroUtils.getCurrentUser();
		boolean flag = culturalrelicsService.plusAccess(user.getOpenid(),
				culturalrelicsId);
		return flag ? result.success("增加访问量成功") : result.failure("增加访问量失败");
	}

	@PostMapping("/plusOrMinusThumbUp")
	@ApiOperation(value = "根据是否点赞的记录来决定是增加点赞还是删除对应点赞", httpMethod = "POST", response = Result.class, notes = "如果有点赞记录则删除，如果没有点赞记录则增加")
	@ApiImplicitParams({ @ApiImplicitParam(name = "culturalrelicsId", value = "文物id", required = true, dataType = "Integer", paramType = "query") })
	public Result<String> plusOrMinusThumbUp(Integer culturalrelicsId) {
		Result<String> result = new Result<String>();
		User user = ShiroUtils.getCurrentUser();
		boolean flag = culturalrelicsService.plusOrMinusThumbUp(user.getOpenid(), culturalrelicsId);
		return flag ? result.success("点赞成功") : result.failure("取消点赞成功");
	}

	@GetMapping("/findCulturalrelics")
	@ApiOperation(value = "通过文物id查询文物", httpMethod = "GET", response = Culturalrelics.class, notes = "通过文物id查询文物")
	@ApiImplicitParams({ @ApiImplicitParam(name = "culturalrelicsId", value = "文物id", required = true, dataType = "Integer", paramType = "query") })
	public Culturalrelics findCulturalrelics(Integer culturalrelicsId) {
		User user = ShiroUtils.getCurrentUser();
		culturalrelicsService.plusAccess(user.getOpenid(), culturalrelicsId);
		return culturalrelicsService.findCulturalrelics(culturalrelicsId);
	}

	@GetMapping("/likeCulturalrelics")
	@ApiOperation(value = "搜索文物", httpMethod = "GET", response = List.class, notes = "通过关键字搜索文物")
	@ApiImplicitParams({ @ApiImplicitParam(name = "condition", value = "文物id", required = true, dataType = "Integer", paramType = "query") })
	public List<Culturalrelics> likeCulturalrelics(String condition,
			Integer themeId) {
		if (condition.length() == 0) {
			return culturalrelicsService.selectculturalrelicsByThemeId(themeId);
		}
		return culturalrelicsService
				.selectCulturalrelicsLikecondition(condition);
	}
}
