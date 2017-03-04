package com.dopakoala.douba.entity;

import java.io.Serializable;

import java.sql.Timestamp;

public class HistoryMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String appid;

	private String classname;

	private String content;

	private Timestamp dateTime;

	private String fileName;

	private Integer fileSize;

	private String fileType;

	private String fileUrl;

	private Integer formUserId;

	private Integer groupId;

	private String imgUrl;

	private Double latitude;

	private Double longitude;

	private String msgUid;

	private String poi;

	private Integer targetType;

	private String title;

	private Integer toUserId;

	private String turnUrl;

	private String voiceUrl;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Integer getFormUserId() {
		return this.formUserId;
	}

	public void setFormUserId(Integer formUserId) {
		this.formUserId = formUserId;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getMsgUid() {
		return this.msgUid;
	}

	public void setMsgUid(String msgUid) {
		this.msgUid = msgUid;
	}

	public String getPoi() {
		return this.poi;
	}

	public void setPoi(String poi) {
		this.poi = poi;
	}

	public Integer getTargetType() {
		return this.targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getToUserId() {
		return this.toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public String getTurnUrl() {
		return this.turnUrl;
	}

	public void setTurnUrl(String turnUrl) {
		this.turnUrl = turnUrl;
	}

	public String getVoiceUrl() {
		return this.voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

}