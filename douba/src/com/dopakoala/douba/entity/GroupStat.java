package com.dopakoala.douba.entity;

import java.io.Serializable;

public class GroupStat implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer gid;

	private Integer number;

	private Integer qunnumber;

	private Integer topic;

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

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getQunnumber() {
		return this.qunnumber;
	}

	public void setQunnumber(Integer qunnumber) {
		this.qunnumber = qunnumber;
	}

	public Integer getTopic() {
		return this.topic;
	}

	public void setTopic(Integer topic) {
		this.topic = topic;
	}

}