package com.zhongda.museum.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.model.Access;
import com.zhongda.museum.service.AccessService;

@RestController
@RequestMapping("/access")
@Api(tags = { "访问量操作接口" })
public class AccessController {

	@Resource
	private AccessService accessService;

	@PostMapping("/countAccess")
	@ApiOperation(value = "统计某一文物的访问量", httpMethod = "POST", response = List.class, notes = "统计某一文物的访问量")
	@ApiImplicitParams({ @ApiImplicitParam(name = "文物id", value = "relicId", required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "起始时间", value = "startDate", required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "结束时间", value = "endDate", required = true, dataType = "Date", paramType = "query")})
	public List<Access> countAccess(Integer relicId, Date startDate, Date endDate) {
		return accessService.countAccess(relicId, startDate, endDate);
	}
	
	@InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }
}
