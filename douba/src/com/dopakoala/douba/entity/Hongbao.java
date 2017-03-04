package com.dopakoala.douba.entity;

import java.sql.Timestamp;

import javax.persistence.Transient;

public class Hongbao {
	private Integer id;

	/**
	 * 红包编码
	 */
	private String code;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 发红包的人
	 */
	private Integer uid;

	/**
	 * 跑团id
	 */
	private Integer gid;

	/**
	 * 红包类型
	 */
	private String type;

	/**
	 * 面额
	 */
	private Long money;

	/**
	 * 剩余金额
	 */
	private Long leftmoney;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 红包口令
	 */
	private String password;

	/**
	 * 发送上限人数
	 */
	private Integer maxnum;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 创建人
	 */
	private Integer createUser;

	/**
	 * 修改时间
	 */
	private Timestamp modifyTime;

	/**
	 * 修改人
	 */
	private Integer modifyAccount;

	/**
	 * 红包返现状态
	 */
	private Integer returnStatus;
	
	/**
	 * 红包被抢完时间
	 */
	private Long time;

	@Transient
	private Integer page;

	@Transient
	private Integer pagesize;

	@Transient
	private String sqlWhere;

	@Transient
	private Timestamp date1;

	@Transient
	private Timestamp date2;

	@Transient
	private String dateStr;

	@Transient
	private String nickname;

	@Transient
	private String groupname;

	@Transient
	private String mobile;
	
	@Transient
	private String thumbnail;
	
	@Transient
	private Long limitMoney;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Long getLeftmoney() {
		return leftmoney;
	}

	public void setLeftmoney(Long leftmoney) {
		this.leftmoney = leftmoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMaxnum() {
		return maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyAccount() {
		return modifyAccount;
	}

	public void setModifyAccount(Integer modifyAccount) {
		this.modifyAccount = modifyAccount;
	}

	public Integer getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Long getLimitMoney() {
		return limitMoney;
	}

	public void setLimitMoney(Long limitMoney) {
		this.limitMoney = limitMoney;
	}
}