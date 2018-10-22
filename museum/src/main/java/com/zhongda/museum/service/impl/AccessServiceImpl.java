package com.zhongda.museum.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhongda.museum.mapper.AccessMapper;
import com.zhongda.museum.model.Access;
import com.zhongda.museum.service.AccessService;

@Service
public class AccessServiceImpl implements AccessService {

	@Resource
	private AccessMapper accessMapper;

	@Override
	public List<Access> countAccess(Integer relicId, Date startDate, Date endDate) {
		return accessMapper.countAccess(relicId, startDate, endDate);
	}
}
