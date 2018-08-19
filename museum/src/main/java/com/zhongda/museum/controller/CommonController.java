package com.zhongda.museum.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.constant.WeiXinConfigConstant;
import com.zhongda.museum.model.JsapiTicket;
import com.zhongda.museum.utils.SignUtils;
import com.zhongda.museum.utils.WeiXinUtils;

@RestController
@RequestMapping("/common")
@Api(tags = { "常用操作接口" })
public class CommonController {

	private static Logger logger = LoggerFactory.getLogger(CommonController.class);

	/**
	 * 微信消息接收和token验证(验证消息来源是否是微信服务器)
	 * 
	 * @param signature
	 *            微信加密签名
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param echostr
	 *            随机字符串
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/checkToken")
	public void validToken(String signature, String timestamp, String nonce,
			String echostr, HttpServletResponse response) throws IOException {
		PrintWriter print = null;
		logger.info("开始签名校验");
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (signature != null
				&& SignUtils.checkSignature(signature, timestamp, nonce)) {
			logger.info("签名校验通过。");
			print = response.getWriter();
			print.write(echostr);// 如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
			print.flush();
		} else {
			logger.error("签名校验失败。");
		}
	}
	
	@GetMapping("/sianUrl")
	@ApiOperation(value = "获取对应url的签名信息", httpMethod = "GET", response = Map.class, notes = "获取对应url的签名信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "url", value = "url路径", required = true, dataType = "String", paramType = "query") })
	public Map<String, String> sianUrl(String url) {
		JsapiTicket jsapiTicket = WeiXinUtils.getJsapiTicket();
		Map<String, String> result = SignUtils.sign(jsapiTicket.getTicket(), url);
		result.put("appId", WeiXinConfigConstant.APP_ID);
		return result;
	}
}
