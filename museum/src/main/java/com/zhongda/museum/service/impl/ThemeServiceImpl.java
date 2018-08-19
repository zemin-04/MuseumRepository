package com.zhongda.museum.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhongda.museum.mapper.ThemeMapper;
import com.zhongda.museum.model.Theme;
import com.zhongda.museum.service.ThemeService;

@Service
public class ThemeServiceImpl implements ThemeService {

	@Resource
	private ThemeMapper themeMapper;

	@Override
	public List<Theme> selectAllTheme() {
		return themeMapper.selectAllTheme();
	}

	@Override
	public Theme selectCulreliBuThemeId(Integer themeId) {
		return themeMapper.selectCulreliBuThemeId(themeId);
	}

	@Override
	public List<Theme> selectThemeRelics() {
		return themeMapper.selectThemeRelics();
	}
}
