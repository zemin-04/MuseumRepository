package com.zhongda.museum.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.model.Theme;
import com.zhongda.museum.service.ThemeService;

@RestController
public class ThemeContorller {

	@Resource
	private ThemeService themeService;

	@RequestMapping("/allTheme")
	public List<Theme> allTheme() {
		return themeService.selectAllTheme();
	}

	@RequestMapping("/themeCul")
	public Theme themeCul(Integer themeId) {
		return themeService.selectCulreliBuThemeId(themeId);
	}
}
