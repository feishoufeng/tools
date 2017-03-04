package com.dopakoala.douba.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class GroupLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String content;

	private Timestamp createTime;

	private Integer createUser;

	private Integer gid;

	private short level;

	private String levelrule;

	private Integer modifyAccount;

	private Timestamp modifyTime;

	private String name;

	private Timestamp sortid;

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

	public short getLevel() {
		return this.level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public String getLevelrule() {
		return this.levelrule;
	}

	public void setLevelrule(String levelrule) {
		this.levelrule = levelrule;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getSortid() {
		return this.sortid;
	}

	public void setSortid(Timestamp sortid) {
		this.sortid = sortid;
	}

}