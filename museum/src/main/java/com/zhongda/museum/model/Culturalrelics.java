package com.zhongda.museum.model;

public class Culturalrelics {
	private Integer culturalrelicsId;

	private Integer culturalrelicThemeid;

	private String culturalrelicsName;

	private String culturalrelicsNumber;

	private String culturalrelicsIntroduction;

	private String culturalrelicsImageurl;

	private String culturalrelicsMusicurl;

	private Integer culturalrelicsZan;

	private Integer culturalrelicsAccess;

	private Integer culturalrelicsSort;

	private String culturalrelicsPposition;

	public Integer getCulturalrelicsId() {
		return culturalrelicsId;
	}

	public void setCulturalrelicsId(Integer culturalrelicsId) {
		this.culturalrelicsId = culturalrelicsId;
	}

	public Integer getCulturalrelicThemeid() {
		return culturalrelicThemeid;
	}

	public void setCulturalrelicThemeid(Integer culturalrelicThemeid) {
		this.culturalrelicThemeid = culturalrelicThemeid;
	}

	public String getCulturalrelicsName() {
		return culturalrelicsName;
	}

	public void setCulturalrelicsName(String culturalrelicsName) {
		this.culturalrelicsName = culturalrelicsName == null ? null
				: culturalrelicsName.trim();
	}

	public String getCulturalrelicsNumber() {
		return culturalrelicsNumber;
	}

	public void setCulturalrelicsNumber(String culturalrelicsNumber) {
		this.culturalrelicsNumber = culturalrelicsNumber == null ? null
				: culturalrelicsNumber.trim();
	}

	public String getCulturalrelicsIntroduction() {
		return culturalrelicsIntroduction;
	}

	public void setCulturalrelicsIntroduction(String culturalrelicsIntroduction) {
		this.culturalrelicsIntroduction = culturalrelicsIntroduction == null ? null
				: culturalrelicsIntroduction.trim();
	}

	public String getCulturalrelicsImageurl() {
		return culturalrelicsImageurl;
	}

	public void setCulturalrelicsImageurl(String culturalrelicsImageurl) {
		this.culturalrelicsImageurl = culturalrelicsImageurl == null ? null
				: culturalrelicsImageurl.trim();
	}

	public String getCulturalrelicsMusicurl() {
		return culturalrelicsMusicurl;
	}

	public void setCulturalrelicsMusicurl(String culturalrelicsMusicurl) {
		this.culturalrelicsMusicurl = culturalrelicsMusicurl == null ? null
				: culturalrelicsMusicurl.trim();
	}

	public Integer getCulturalrelicsZan() {
		return culturalrelicsZan;
	}

	public void setCulturalrelicsZan(Integer culturalrelicsZan) {
		this.culturalrelicsZan = culturalrelicsZan;
	}

	public Integer getCulturalrelicsAccess() {
		return culturalrelicsAccess;
	}

	public void setCulturalrelicsAccess(Integer culturalrelicsAccess) {
		this.culturalrelicsAccess = culturalrelicsAccess;
	}

	public Integer getCulturalrelicsSort() {
		return culturalrelicsSort;
	}

	public void setCulturalrelicsSort(Integer culturalrelicsSort) {
		this.culturalrelicsSort = culturalrelicsSort;
	}

	public String getCulturalrelicsPposition() {
		return culturalrelicsPposition;
	}

	public void setCulturalrelicsPposition(String culturalrelicsPposition) {
		this.culturalrelicsPposition = culturalrelicsPposition;
	}

}