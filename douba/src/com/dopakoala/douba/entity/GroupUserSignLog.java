package com.dopakoala.douba.entity;

import java.io.Serializable;

import java.sql.Timestamp;

public class GroupUserSignLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Double altitude;

	private Integer cardId;

	private Timestamp createTime;

	private Integer createUser;

	private Integer gid;

	private Integer modifyAccount;

	private Timestamp modifyTime;

	private Long period;

	private String pic;

	private Double runmiles;

	private Integer signId;

	private String type;

	private Integer uid;

	private String thumbnail;

	private String nickname;

	private String avatar;

	private Integer page;

	private Integer pagesize;

	private Timestamp starttime;

	private Timestamp endtime;

	private Integer rank;

	private Integer targetmiles;

	private Double nowmiles;

	private Double revokemiles;

	private Integer count;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getAltitude() {
		return this.altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Integer getCardId() {
		return this.cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
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

	public Long getPeriod() {
		return this.period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Double getRunmiles() {
		return this.runmiles;
	}

	public void setRunmiles(Double runmiles) {
		this.runmiles = runmiles;
	}

	public Integer getSignId() {
		return this.signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public Double getNowmiles() {
		return nowmiles;
	}

	public void setNowmiles(Double nowmiles) {
		this.nowmiles = nowmiles;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Double getRevokemiles() {
		return revokemiles;
	}

	public void setRevokemiles(Double revokemiles) {
		this.revokemiles = revokemiles;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}