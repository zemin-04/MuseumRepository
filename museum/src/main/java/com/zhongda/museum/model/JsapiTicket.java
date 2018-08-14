package com.zhongda.museum.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsapiTicket {

	@JsonProperty("errcode")
	private int errcode;

	@JsonProperty("errmsg")
	private String errmsg;

	@JsonProperty("ticket")
	private String ticket;

	@JsonProperty("expires_in")
	private int expiresIn;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
