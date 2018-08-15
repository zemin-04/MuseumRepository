package com.zhongda.museum.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessToken implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private int expiresIn;
	
    public String getAccessToken() {
        return accessToken;
    }
 
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
 
    public int getExpiresIn() {
        return expiresIn;
    }
 
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
 
}