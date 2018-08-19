package com.zhongda.museum.service;

import java.util.List;

import com.zhongda.museum.model.Theme;

public interface ThemeService {

	/**
	 * 查询所有展厅
	 * 
	 * @return
	 */
	List<Theme> selectAllTheme();

	/**
	 * 查询展厅下的所有文物
	 * 
	 * @param themeId
	 * @return
	 */
	Theme selectCulreliBuThemeId(Integer themeId);
	
	/**
	 * 查询所有展厅和展厅下的所有文物
	 * @return
	 */
	List<Theme> selectThemeRelics();
}
