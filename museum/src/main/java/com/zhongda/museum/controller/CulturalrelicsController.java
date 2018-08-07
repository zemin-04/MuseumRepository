package com.zhongda.museum.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.service.CulturalrelicsService;

@RestController
@RequestMapping("/culturalrelics")
@Api(tags = { "文物操作接口" })
public class CulturalrelicsController {
	
	@Resource
	private CulturalrelicsService culturalrelicsService;

	@RequestMapping("/addAccess")
	@ApiOperation(value = "对应文物增加一次访问量", httpMethod = "POST", response = Void.class, notes = "根据文物id对应文物增加一次访问量")
	@ApiImplicitParams({ @ApiImplicitParam(name = "culturalrelicsId", value = "展厅id", required = true, dataType = "Integer", paramType = "query") })
	public void addAccess(Integer culturalrelicsId) {
		
	}
}
