package com.zhongda.museum.service.impl;

import java.util.List;

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
	public boolean plusOrMinusThumbUp(String openid, Integer culturalrelicsId) {
		ThumbUp thumbUp = thumbUpMapper.selectByOpenidAndCulturalrelicsId(openid, culturalrelicsId);
		if (null == thumbUp) {
			//如果没有点赞记录，则增加一次点赞记录
			culturalrelicsMapper.updatePlusThumbUpByCulturalrelicsId(culturalrelicsId);
			thumbUp = new ThumbUp(openid, culturalrelicsId);
			thumbUpMapper.insertSelective(thumbUp);
			return true;
		} else {
			//如果有点赞记录，则删除当前点赞记录
			culturalrelicsMapper.updateMinusThumbUpByCulturalrelicsId(culturalrelicsId);
			thumbUpMapper.deleteByPrimaryKey(thumbUp.getThumbUpId());
			return false;
		}
	}

	@Override
	public List<Culturalrelics> selectCulturalrelicsLikecondition(
			String condition) {
		return culturalrelicsMapper
				.selectCulturalrelicsLikecondition(condition);
	}

	@Override
	public List<Culturalrelics> selectculturalrelicsByThemeId(Integer themeId) {
		return culturalrelicsMapper.selectculturalrelicsByThemeId(themeId);
	}

	@Override
	public List<Culturalrelics> selectAllCulturalrelics() {
		List<Culturalrelics> culturalrelicsList = culturalrelicsMapper.selectAllCulturalrelics();
		return culturalrelicsList;
	}

	@Override
	public List<Culturalrelics> selectRelicsByThemeId(Integer themeId,
			Integer accessSort, Integer zanSort) {
		List<Culturalrelics> culturalrelicsList = culturalrelicsMapper.selectRelicsByThemeId(themeId, accessSort, zanSort);
		return culturalrelicsList;
	}

}
