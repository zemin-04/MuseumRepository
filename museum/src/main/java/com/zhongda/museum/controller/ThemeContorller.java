package com.zhongda.museum.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.model.Theme;
import com.zhongda.museum.service.ThemeService;

@RestController
@RequestMapping("/theme")
@Api(tags = { "主题操作接口" })
public class ThemeContorller {

	@Resource
	private ThemeService themeService;

	@GetMapping("/allTheme")
	@ApiOperation(value = "查询所有展厅", httpMethod = "GET", response = List.class, notes = "查询所有展厅")
	public List<Theme> allTheme() {
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return themeService.selectAllTheme();
	}

	@GetMapping("/themeCul")
	@ApiOperation(value = "查询展厅下的所有文物", httpMethod = "GET", response = Theme.class, notes = "根据展厅id查询展厅下的所有文物")
	@ApiImplicitParams({ @ApiImplicitParam(name = "themeId", value = "展厅id", required = true, dataType = "Integer", paramType = "query") })
	public Theme themeCul(Integer themeId) {
		return themeService.selectCulreliBuThemeId(themeId);
	}

	@GetMapping("/themeRelics")
	@ApiOperation(value = "查询所有展厅和展厅下的所有文物", httpMethod = "GET", response = Theme.class, notes = "查询所有展厅和展厅下的所有文物")
	public List<Theme> themeRelics() {
		return themeService.selectThemeRelics();
	}
	
	@GetMapping("/themeCulFirstSix")
	@ApiOperation(value = "查询展厅下的所有文物", httpMethod = "GET", response = Theme.class, notes = "查询每个展厅下的访问量排名前六的文物")
	public List<Theme> themeCulFirstSix() {
		return themeService.selectCulFirstSixAllTheme();
	}
	
	@GetMapping("/test")
	@ApiOperation(value = "查询所有展厅和展厅下的所有文物", httpMethod = "GET", response = Theme.class, notes = "查询所有展厅和展厅下的所有文物")
	public String test() {
		return "hello ";
	}
}
