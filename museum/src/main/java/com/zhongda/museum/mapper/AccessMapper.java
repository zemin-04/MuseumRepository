package com.zhongda.museum.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhongda.museum.model.Access;

@Mapper
public interface AccessMapper {
    int deleteByPrimaryKey(Integer accessId);

    int insert(Access record);

    int insertSelective(Access record);

    Access selectByPrimaryKey(Integer accessId);

    int updateByPrimaryKeySelective(Access record);

    int updateByPrimaryKey(Access record);

	/**
	 * 统计某一文物的访问量
	 * @param relicId 文物id
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @return
	 */
	List<Access> countAccess(@Param(value = "culturalrelicsId")Integer relicId, @Param(value = "startDate")Date startDate,
			@Param(value = "endDate")Date endDate);
}