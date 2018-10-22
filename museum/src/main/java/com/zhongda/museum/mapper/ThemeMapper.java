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
	
	/**
	 * 查询所有展厅和展厅下的所有文物
	 * @return
	 */
	List<Theme> selectThemeRelics();
	
	/**
	 * 查询每个展厅下的访问量排名前六的文物
	 * @return
	 */
	List<Theme> selectCulFirstSixAllTheme();
}