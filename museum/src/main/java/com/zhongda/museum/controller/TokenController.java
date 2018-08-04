package com.zhongda.museum.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongda.museum.utils.WeiXinUtil;

@RestController
public class TokenController {
	
	 /**
     * 微信消息接收和token验证
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
	@RequestMapping("/token")
    public void validToken(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException{
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
        	System.out.println("开始签名校验");
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && WeiXinUtil.checkSignature(signature, timestamp, nonce)) {
            	System.out.println("签名校验通过。");
            	print = response.getWriter();
                print.write(echostr);//如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
                print.flush();
            }else{
            	System.out.println("签名校验失败。");
            }
        }
	}
}
