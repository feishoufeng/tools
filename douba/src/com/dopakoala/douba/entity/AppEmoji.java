package com.dopakoala.douba.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class AppEmoji implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Timestamp createTime;

	private Integer createUser;

	private Integer download;

	private String file;

	private Integer gid;

	private Integer modifyAccount;

	private Timestamp modifyTime;

	private String pic;

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

	public Integer getDownload() {
		return this.download;
	}

	public void setDownload(Integer download) {
		this.download = download;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
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

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}