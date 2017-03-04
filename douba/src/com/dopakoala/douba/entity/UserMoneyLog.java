package com.dopakoala.douba.entity;

import java.sql.Timestamp;

import javax.persistence.Transient;

public class UserMoneyLog {
	private Integer id;

	/**
	 * 用户id
	 */
	private Integer uid;

	/**
	 * 方式
	 */
	private String action;

	/**
	 * 金额
	 */
	private Long money;

	/**
	 * 剩余金额
	 */
	private Long leftmoney;

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
	 * 支出类型
	 */
	private Integer type;

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
	private String mobile;
	
	@Transient
	private Integer num;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}