package com.zhongda.museum.service;

import java.util.Date;
import java.util.List;

import com.zhongda.museum.model.Access;

public interface AccessService {
	
	/**
	 * 根据主题以及访问量和点赞量的顺序查询文物
	 * @param themeId
	 * @param accessSort
	 * @param zanSort
	 * @return
	 */
	List<Access> countAccess(Integer relicId, Date startDate, Date endDate);

}
