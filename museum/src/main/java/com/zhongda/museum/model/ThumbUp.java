package com.zhongda.museum.model;

import java.util.Date;

public class ThumbUp {
    private Integer thumbUpId;

    private String openid;

    private Integer culturalrelicsId;

    private Date thumbUpTime;

    public Integer getThumbUpId() {
        return thumbUpId;
    }

    public void setThumbUpId(Integer thumbUpId) {
        this.thumbUpId = thumbUpId;
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

    public Date getThumbUpTime() {
        return thumbUpTime;
    }

    public void setThumbUpTime(Date thumbUpTime) {
        this.thumbUpTime = thumbUpTime;
    }
}