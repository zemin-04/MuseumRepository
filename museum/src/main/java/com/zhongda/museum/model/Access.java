package com.zhongda.museum.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Access {
    private Integer accessId;

    private String openid;

    private Integer culturalrelicsId;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date accessTime;
    
    private Integer accessNumber;
    
    public Access(){
    }
    
    public Access(String openid, Integer culturalrelicsId) {
		this.openid = openid;
		this.culturalrelicsId = culturalrelicsId;
		this.accessTime = new Date();
	}

	public Integer getAccessId() {
        return accessId;
    }
	
    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getCulturalrelicsId() {
        return culturalrelicsId;
    }

    public void setCulturalrelicsId(Integer culturalrelicsId) {
        this.culturalrelicsId = culturalrelicsId;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

	public Integer getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(Integer accessNumber) {
		this.accessNumber = accessNumber;
	}
}