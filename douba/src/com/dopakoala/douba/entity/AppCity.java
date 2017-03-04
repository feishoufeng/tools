package com.dopakoala.douba.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class AppCity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer layer;

	private Integer lcode;

	private String name;

	private String paramSetting;

	private Integer pid;

	private String pinyin;

	private Timestamp sortId;

	private Integer status;

	private String zipcode;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLayer() {
		return this.layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Integer getLcode() {
		return this.lcode;
	}

	public void setLcode(Integer lcode) {
		this.lcode = lcode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParamSetting() {
		return this.paramSetting;
	}

	public void setParamSetting(String paramSetting) {
		this.paramSetting = paramSetting;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Timestamp getSortId() {
		return this.sortId;
	}

	public void setSortId(Timestamp sortId) {
		this.sortId = sortId;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}