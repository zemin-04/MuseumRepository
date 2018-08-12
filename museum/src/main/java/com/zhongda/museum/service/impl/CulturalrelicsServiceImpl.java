package com.zhongda.museum.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongda.museum.mapper.AccessMapper;
import com.zhongda.museum.mapper.CulturalrelicsMapper;
import com.zhongda.museum.mapper.ThumbUpMapper;
import com.zhongda.museum.model.Access;
import com.zhongda.museum.model.Culturalrelics;
import com.zhongda.museum.model.ThumbUp;
import com.zhongda.museum.service.CulturalrelicsService;

@Service
public class CulturalrelicsServiceImpl implements CulturalrelicsService {

	@Resource
	private CulturalrelicsMapper culturalrelicsMapper;
	@Resource
	private AccessMapper accessMapper;
	@Resource
	private ThumbUpMapper thumbUpMapper;

	@Override
	public Culturalrelics findCulturalrelics(Integer culturalrelicsId) {
		return culturalrelicsMapper.selectByPrimaryKey(culturalrelicsId);
	}
	
	@Transactional
	@Override
	public boolean plusAccess(String openid, Integer culturalrelicsId) {
		culturalrelicsMapper.updateAccessByCulturalrelicsId(culturalrelicsId);
		Access access = new Access(openid, culturalrelicsId);
		accessMapper.insertSelective(access);
		return true;
	}
	
	@Transactional
	@Override
	public boolean plusThumbUp(String openid, Integer culturalrelicsId) {
		ThumbUp thumbUp = thumbUpMapper.selectByOpenidAndCulturalrelicsId(openid, culturalrelicsId);
		if(null == thumbUp){
			culturalrelicsMapper.updatePlusThumbUpByCulturalrelicsId(culturalrelicsId);
			thumbUp = new ThumbUp(openid, culturalrelicsId);
			thumbUpMapper.insertSelective(thumbUp);
			return true;
		}
		return false;
	}
	
	@Transactional
	@Override
	public boolean minusThumbUp(String openid, Integer culturalrelicsId) {
		ThumbUp thumbUp = thumbUpMapper.selectByOpenidAndCulturalrelicsId(openid, culturalrelicsId);
		if(null != thumbUp){
			culturalrelicsMapper.updateMinusThumbUpByCulturalrelicsId(culturalrelicsId);
			thumbUpMapper.deleteByPrimaryKey(thumbUp.getThumbUpId());
			return true;
		}
		return false;
	}
}
