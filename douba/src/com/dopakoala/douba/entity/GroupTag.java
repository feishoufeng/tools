package com.dopakoala.douba.entity;

import java.io.Serializable;

public class GroupTag implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;

	private Integer gid;

	private String name;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}