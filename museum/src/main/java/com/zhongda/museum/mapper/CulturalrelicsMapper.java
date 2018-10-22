package com.zhongda.museum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhongda.museum.model.Culturalrelics;

@Mapper
public interface CulturalrelicsMapper {
	int deleteByPrimaryKey(Integer culturalrelicsId);

	int insert(Culturalrelics record);

	int insertSelective(Culturalrelics record);

	/**
	 * 通过ID查询文物
	 * 
	 * @param culturalrelicsId
	 *            文物id
	 * @return
	 */
	Culturalrelics selectByPrimaryKey(Integer culturalrelicsId);

	int updateByPrimaryKeySelective(Culturalrelics record);

	int updateByPrimaryKey(Culturalrelics record);

	/**
	 * 根据文物id增加一次对应的文物访问量
	 * 
	 * @param culturalrelicsId
	 */
	int updateAccessByCulturalrelicsId(Integer culturalrelicsId);

	/**
	 * 根据文物id增加一次对应的文物点赞量
	 * 
	 * @param culturalrelicsId
	 */
	int updatePlusThumbUpByCulturalrelicsId(Integer culturalrelicsId);

	/**
	 * 根据文物id减少一次对应的文物点赞量 通过ID查询文物
	 * 
	 * @param culturalrelicsId
	 */
	int updateMinusThumbUpByCulturalrelicsId(Integer culturalrelicsId);

	Culturalrelics selectculturalrelicsById(
			@Param(value = "culturalrelicsId") Integer culturalrelicsId);

	/**
	 * 搜索符合条件的文物
	 * 
	 * @param condition
	 * @return
	 */
	List<Culturalrelics> selectCulturalrelicsLikecondition(
			@Param(value = "condition") String condition);

	/**
	 * 查询展厅下的文物
	 * 
	 * @param themeId
	 * @return
	 */
	List<Culturalrelics> selectculturalrelicsByThemeId(
			@Param(value = "themeId") Integer themeId);
	
	/**
	 * 搜索所有文物
	 * @return
	 */
	List<Culturalrelics> selectAllCulturalrelics();
	
	/**
	 * 根据主题以及访问量和点赞量的顺序查询文物
	 * @param themeId
	 * @param accessSort
	 * @param zanSort
	 * @return
	 */
	List<Culturalrelics> selectRelicsByThemeId(@Param(value = "themeId") Integer themeId,
			@Param(value = "accessSort")  Integer accessSort, @Param(value = "zanSort")  Integer zanSort);
}