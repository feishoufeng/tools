package com.dopakoala.douba.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer gid;

	private String avatar;

	private String content;

	private Timestamp createTime;

	private Integer createUser;

	private Integer hotnum;

	private Integer ismain;

	private Integer istemp;

	private String condition;

	private Integer modifyAccount;

	private Timestamp modifyTime;

	private String name;

	private Integer number;

	private Integer pid;

	private String qrcode;

	private String regtype;

	private String thumbnail;

	private Timestamp sortid;

	private Integer status;

	private Integer type;

	private String typeName;

	private Integer page;

	private Integer pagesize;

	private Integer uid;

	private Integer isadd;

	private String notice;

	private String nickname;

	private Integer mainGroupGid;

	private Integer root;

	private Integer open;

	private List<String> avatars;

	private List<String> thumbnails;

	private String sqlWhere;

	private Timestamp date1;

	private Timestamp date2;

	private String dateStr;

	private String mobile;

	public List<String> getAvatars() {
		return avatars;
	}

	public void setAvatars(List<String> avatars) {
		this.avatars = avatars;
	}

	public List<String> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(List<String> thumbnails) {
		this.thumbnails = thumbnails;
	}

	public Group() {
	}

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public Integer getHotnum() {
		return this.hotnum;
	}

	public void setHotnum(Integer hotnum) {
		this.hotnum = hotnum;
	}

	public Integer getIsmain() {
		return this.ismain;
	}

	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
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

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getQrcode() {
		return this.qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getRegtype() {
		return this.regtype;
	}

	public void setRegtype(String regtype) {
		this.regtype = regtype;
	}

	public Timestamp getSortid() {
		return this.sortid;
	}

	public void setSortid(Timestamp sortid) {
		this.sortid = sortid;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getIsadd() {
		return isadd;
	}

	public void setIsadd(Integer isadd) {
		this.isadd = isadd;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getMainGroupGid() {
		return mainGroupGid;
	}

	public void setMainGroupGid(Integer mainGroupGid) {
		this.mainGroupGid = mainGroupGid;
	}

	public Integer getIstemp() {
		return istemp;
	}

	public void setIstemp(Integer istemp) {
		this.istemp = istemp;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getRoot() {
		return root;
	}

	public void setRoot(Integer root) {
		this.root = root;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getSqlWhere() {
		return sqlWhere;
	}

	public void setSqlWhere(String sqlWhere) {
		this.sqlWhere = sqlWhere;
	}

	public Timestamp getDate1() {
		return date1;
	}

	public void setDate1(Timestamp date1) {
		this.date1 = date1;
	}

	public Timestamp getDate2() {
		return date2;
	}

	public void setDate2(Timestamp date2) {
		this.date2 = date2;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}