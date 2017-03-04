package com.dopakoala.douba.entity;

import java.io.Serializable;

import java.sql.Timestamp;

public class UserFriend implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Timestamp createTime;

	private String mark;

	private Integer status;

	private Integer touid;

	private Integer uid;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTouid() {
		return this.touid;
	}

	public void setTouid(Integer touid) {
		this.touid = touid;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}