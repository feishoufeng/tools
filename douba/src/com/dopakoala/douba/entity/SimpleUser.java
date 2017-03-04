package com.dopakoala.douba.entity;

import java.io.Serializable;

public class SimpleUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private String avatar;
	private String nickname;
	private Integer uid;
	private String thumbnail;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

}
