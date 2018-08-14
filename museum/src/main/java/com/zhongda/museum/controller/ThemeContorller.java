package com.zhongda.museum.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.model.JsapiTicket;
import com.zhongda.museum.model.Theme;
import com.zhongda.museum.service.ThemeService;
import com.zhongda.museum.utils.SignUtils;
import com.zhongda.museum.utils.WeiXinUtils;

@RestController
@RequestMapping("/theme")
@Api(tags = { "主题操作接口" })
public class ThemeContorller {

	@Resource
	private ThemeService themeService;

	@GetMapping("/allTheme")
	@ApiOperation(value = "查询所有展厅", httpMethod = "GET", response = List.class, notes = "查询所有展厅")
	public List<Theme> allTheme() {
		return themeService.selectAllTheme();
	}

	@GetMapping("/themeCul")
	@ApiOperation(value = "查询展厅下的所有文物", httpMethod = "GET", response = Theme.class, notes = "根据展厅id查询展厅下的所有文物")
	@ApiImplicitParams({ @ApiImplicitParam(name = "themeId", value = "展厅id", required = true, dataType = "Integer", paramType = "query") })
	public Theme themeCul(Integer themeId) {
		return themeService.selectCulreliBuThemeId(themeId);
	}

	@GetMapping("/test")
	@ApiOperation(value = "获取对应url的签名信息", httpMethod = "GET", response = Map.class, notes = "获取对应url的签名信息")
	public Map<String, String> test(String url) {
		JsapiTicket jsapiTicket = WeiXinUtils.getJsapiTicket();
		Map<String, String> result = SignUtils.sign(jsapiTicket.getTicket(), url);
		return result;
	}
}
