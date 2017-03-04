package com.dopakoala.douba.entity;

import java.sql.Timestamp;

public class GroupPace {
    private Integer id;

    /**
    * 群id
    */
    private Integer gid;

    /**
    * 最低配速
    */
    private Integer minPace;

    /**
    * 跑步类型
    */
    private Integer type;

    /**
    * 最短距离
    */
    private Double minDistance;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getMinPace() {
        return minPace;
    }

    public void setMinPace(Integer minPace) {
        this.minPace = minPace;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Double minDistance) {
        this.minDistance = minDistance;
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
}