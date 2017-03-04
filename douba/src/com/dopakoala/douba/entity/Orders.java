package com.dopakoala.douba.entity;

import java.sql.Timestamp;

public class Orders {
    private Integer orderId;

    private String type;

    /**
    * 状态
    */
    private Integer status;

    /**
    * 需要支付的金额
    */
    private Long money;

    private Integer uid;

    /**
    * 支付状态
    */
    private Integer payStatus;

    /**
    * 支付时间
    */
    private Long payTime;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
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