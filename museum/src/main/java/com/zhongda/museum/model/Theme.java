package com.zhongda.museum.model;

import java.util.List;

public class Theme {
	private Integer themeId;

	private String themeName;

	private String themePosition;

	private String themeType;

	private String themeDate;

	private Integer themeSort;

	private String themeImageurl;

	private List<Culturalrelics> Culturalrelicss;

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName == null ? null : themeName.trim();
	}

	public String getThemePosition() {
		return themePosition;
	}

	public void setThemePosition(String themePosition) {
		this.themePosition = themePosition == null ? null : themePosition
				.trim();
	}

	public String getThemeType() {
		return themeType;
	}

	public void setThemeType(String themeType) {
		this.themeType = themeType == null ? null : themeType.trim();
	}

	public String getThemeDate() {
		return themeDate;
	}

	public void setThemeDate(String themeDate) {
		this.themeDate = themeDate == null ? null : themeDate.trim();
	}

	public Integer getThemeSort() {
		return themeSort;
	}

	public void setThemeSort(Integer themeSort) {
		this.themeSort = themeSort;
	}

	public String getThemeImageurl() {
		return themeImageurl;
	}

	public void setThemeImageurl(String themeImageurl) {
		this.themeImageurl = themeImageurl == null ? null : themeImageurl
				.trim();
	}

	public List<Culturalrelics> getCulturalrelicss() {
		return Culturalrelicss;
	}

	public void setCulturalrelicss(List<Culturalrelics> culturalrelicss) {
		Culturalrelicss = culturalrelicss;
	}
}