package com.dopakoala.douba.entity;

import java.io.Serializable;

import java.sql.Timestamp;

public class GroupStatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Timestamp endTime;

	private Integer gid;

	private Long period;

	private Double runmiles;

	private String sid;

	private Timestamp startTime;

	private Integer status;

	private Integer type;

	private Integer uid;

	private Integer page;

	private Integer pagesize;

	private Integer rank;

	private Integer targetmiles;

	private String nickname;

	private String avatar;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Long getPeriod() {
		return this.period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public Double getRunmiles() {
		return this.runmiles;
	}

	public void setRunmiles(Double runmiles) {
		this.runmiles = runmiles;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getTargetmiles() {
		return targetmiles;
	}

	public void setTargetmiles(Integer targetmiles) {
		this.targetmiles = targetmiles;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}