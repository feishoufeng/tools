package com.dopakoala.douba.entity;

import java.sql.Timestamp;

public class CatalogType {
    private Integer id;

    /**
    * 菜单名称
    */
    private String name;

    /**
    * 状态
    */
    private Integer status;

    /**
    * 排序
    */
    private Integer orderId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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