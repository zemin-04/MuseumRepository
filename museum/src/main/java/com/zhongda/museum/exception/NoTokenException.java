package com.zhongda.museum.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Title: 自定义的RuntimeException
 * Description:没有传入token做验证时抛出
 * @Author dengzm
 */
public class NoTokenException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	public NoTokenException() {
		super();
	}

	public NoTokenException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
