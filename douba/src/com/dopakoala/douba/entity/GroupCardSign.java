package com.dopakoala.douba.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class GroupCardSign implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Timestamp createTime;

	private Integer createUser;

	private Integer currSignid;

	private Timestamp deadline;

	private Integer endId;

	private Long endTime;

	private String footer;

	private Integer gid;

	private String header;

	private Integer modifyAccount;

	private Timestamp modifyTime;

	private String signList;

	private Integer signUid;

	private Integer startId;

	private Long startTime;

	private Integer status;

	private Double totalmiles;

	private Long time;

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

	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getCurrSignid() {
		return this.currSignid;
	}

	public void setCurrSignid(Integer currSignid) {
		this.currSignid = currSignid;
	}

	public Timestamp getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public Integer getEndId() {
		return this.endId;
	}

	public void setEndId(Integer endId) {
		this.endId = endId;
	}

	public Long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getFooter() {
		return this.footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Integer getModifyAccount() {
		return this.modifyAccount;
	}

	public void setModifyAccount(Integer modifyAccount) {
		this.modifyAccount = modifyAccount;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getSignList() {
		return this.signList;
	}

	public void setSignList(String signList) {
		this.signList = signList;
	}

	public Integer getSignUid() {
		return this.signUid;
	}

	public void setSignUid(Integer signUid) {
		this.signUid = signUid;
	}

	public Integer getStartId() {
		return this.startId;
	}

	public void setStartId(Integer startId) {
		this.startId = startId;
	}

	public Long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getTotalmiles() {
		return this.totalmiles;
	}

	public void setTotalmiles(Double totalmiles) {
		this.totalmiles = totalmiles;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}