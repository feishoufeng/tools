package com.dopakoala.douba.entity;

import java.io.Serializable;

public class GroupApp implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String appname;

	private String name;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppname() {
		return this.appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}