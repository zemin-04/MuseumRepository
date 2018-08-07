package com.zhongda.museum.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.model.Culturalrelics;
import com.zhongda.museum.service.CulturalrelicsService;

@RestController
public class CulturalrelicsContorller {

	@Resource
	private CulturalrelicsService culturalrelicsService;

	@RequestMapping("/culturalrelics")
	public Culturalrelics culturalrelics(Integer culturalrelicsId) {
		return culturalrelicsService.selectculturalrelicsBuId(culturalrelicsId);
	}

}
