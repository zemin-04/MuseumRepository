package com.zhongda.museum.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhongda.museum.mapper.CulturalrelicsMapper;
import com.zhongda.museum.model.Culturalrelics;
import com.zhongda.museum.service.CulturalrelicsService;

@Service
public class CulturalrelicsServiceImpl implements CulturalrelicsService {

	@Resource
	private CulturalrelicsMapper culturalrelicsMapper;

	@Override
	public Culturalrelics selectculturalrelicsBuId(Integer culturalrelicsId) {
		return culturalrelicsMapper.selectculturalrelicsBuId(culturalrelicsId);
	}

}
