package com.zhongda.museum.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.utils.SignUtils;

@RestController
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

}
