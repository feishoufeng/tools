package com.dopakoala.douba.entity;

import java.io.Serializable;

public class GroupUserSign implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;

	private Integer gid;

	private Long lastdate;

	private Double nowmiles;

	private Integer resetdate;

	private Integer targetmiles;

	private Integer uid;

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

	public Long getLastdate() {
		return this.lastdate;
	}

	public void setLastdate(Long lastdate) {
		this.lastdate = lastdate;
	}

	public Double getNowmiles() {
		return this.nowmiles;
	}

	public void setNowmiles(Double nowmiles) {
		this.nowmiles = nowmiles;
	}

	public Integer getResetdate() {
		return this.resetdate;
	}

	public void setResetdate(Integer resetdate) {
		this.resetdate = resetdate;
	}

	public Integer getTargetmiles() {
		return this.targetmiles;
	}

	public void setTargetmiles(Integer targetmiles) {
		this.targetmiles = targetmiles;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}