package com.zhongda.museum.model;

import java.util.Date;

public class Access {
    private Integer accessId;

    private String openid;

    private Integer culturalrelicsId;

    private Date accessTime;

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
}