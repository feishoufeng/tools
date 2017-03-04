package com.dopakoala.douba.entity;

import java.io.Serializable;

import java.sql.Timestamp;

public class GroupTopic implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String content;

	private Timestamp createTime;

	private Integer createUser;

	private Integer gid;

	private Integer istop;

	private Integer likes;

	private Integer modifyAccount;

	private Timestamp modifyTime;

	private Integer shared;

	private Timestamp sortid;

	private String title;

	private String type;

	private String nickname;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getIstop() {
		return this.istop;
	}

	public void setIstop(Integer istop) {
		this.istop = istop;
	}

	public Integer getLikes() {
		return this.likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
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

	public Integer getShared() {
		return this.shared;
	}

	public void setShared(Integer shared) {
		this.shared = shared;
	}

	public Timestamp getSortid() {
		return this.sortid;
	}

	public void setSortid(Timestamp sortid) {
		this.sortid = sortid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}