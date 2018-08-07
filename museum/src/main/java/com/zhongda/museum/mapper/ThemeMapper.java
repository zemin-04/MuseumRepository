package com.zhongda.museum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhongda.museum.model.Theme;

@Mapper
public interface ThemeMapper {
	int deleteByPrimaryKey(Integer themeId);

	int insert(Theme record);

	int insertSelective(Theme record);

	Theme selectByPrimaryKey(Integer themeId);

	int updateByPrimaryKeySelective(Theme record);

	int updateByPrimaryKey(Theme record);

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
	Theme selectCulreliBuThemeId(@Param("themeId") Integer themeId);
}