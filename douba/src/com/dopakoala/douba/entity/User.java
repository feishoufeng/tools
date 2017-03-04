package com.dopakoala.douba.entity;

import java.io.Serializable;

import java.sql.Timestamp;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer uid;

	private String adress;

	private Double advance;

	private String avatar;

	private String birthday;

	private Timestamp createTime;

	private Integer createUser;

	private String email;

	private String graduate;

	private Long logintime;

	private Integer logintimes;

	private String mobile;

	private Integer modifyAccount;

	private Timestamp modifyTime;

	private Long money;

	private String nickname;

	private Long nocashmoney;

	private String password;

	private String passwordSalt;

	private String personalIntroduction;

	private Integer points;

	private String qrCode;

	private String realname;

	private Long regtime;

	private Integer sex;

	private String sign;

	private Integer status;

	private String token;

	private String username;

	private String thumbnail;

	private String adressDetail;

	private Integer page;

	private Integer pagesize;

	private String dateStr;

	private Double runMiles;

	private Timestamp date1;

	private Timestamp date2;

	private String sqlWhere;

	private String openid;

	private Integer createGroup;

	private String payPwd;

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Double getAdvance() {
		return this.advance;
	}

	public void setAdvance(Double advance) {
		this.advance = advance;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGraduate() {
		return this.graduate;
	}

	public void setGraduate(String graduate) {
		this.graduate = graduate;
	}

	public Long getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Long logintime) {
		this.logintime = logintime;
	}

	public Integer getLogintimes() {
		return this.logintimes;
	}

	public void setLogintimes(Integer logintimes) {
		this.logintimes = logintimes;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Long getMoney() {
		return this.money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getNocashmoney() {
		return this.nocashmoney;
	}

	public void setNocashmoney(Long nocashmoney) {
		this.nocashmoney = nocashmoney;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return this.passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String getPersonalIntroduction() {
		return this.personalIntroduction;
	}

	public void setPersonalIntroduction(String personalIntroduction) {
		this.personalIntroduction = personalIntroduction;
	}

	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getQrCode() {
		return this.qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Long getRegtime() {
		return this.regtime;
	}

	public void setRegtime(Long regtime) {
		this.regtime = regtime;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAdressDetail() {
		return adressDetail;
	}

	public void setAdressDetail(String adressDetail) {
		this.adressDetail = adressDetail;
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

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Double getRunMiles() {
		return runMiles;
	}

	public void setRunMiles(Double runMiles) {
		this.runMiles = runMiles;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public String getSqlWhere() {
		return sqlWhere;
	}

	public void setSqlWhere(String sqlWhere) {
		this.sqlWhere = sqlWhere;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getCreateGroup() {
		return createGroup;
	}

	public void setCreateGroup(Integer createGroup) {
		this.createGroup = createGroup;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

}