package com.dopakoala.douba.entity;

import java.sql.Timestamp;

public class HongbaoLog {
    private Integer id;

    /**
    * 红包id
    */
    private Integer hongbaoId;

    /**
    * 红包接受人
    */
    private Integer uid;

    private Long money;

    /**
    * 创建时间
    */
    private Timestamp createTime;

    /**
    * 创建人
    */
    private Integer createUser;
    
    private String nickname;
    
    private String thumbnail;
    
    private Integer isTop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHongbaoId() {
        return hongbaoId;
    }

    public void setHongbaoId(Integer hongbaoId) {
        this.hongbaoId = hongbaoId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
}