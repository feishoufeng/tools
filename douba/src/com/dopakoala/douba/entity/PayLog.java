package com.dopakoala.douba.entity;

public class PayLog {
    private Long payid;

    /**
    * 状态
    */
    private Integer status;

    /**
    * 用户id
    */
    private Integer uid;

    /**
    * 金额
    */
    private Long price;

    /**
    * 订单id
    */
    private String orderId;

    /**
    * 类型
    */
    private String type;

    /**
    * 支付外id
    */
    private String outerid;

    /**
    * 支付接口状态
    */
    private String tradestatus;

    /**
    * 请求参数
    */
    private String param;

    /**
    * 接口回调内容
    */
    private String returnparam;

    /**
    * 添加时间
    */
    private Long addtime;

    /**
    * 最后更新时间
    */
    private Long lasttime;

    public Long getPayid() {
        return payid;
    }

    public void setPayid(Long payid) {
        this.payid = payid;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOuterid() {
        return outerid;
    }

    public void setOuterid(String outerid) {
        this.outerid = outerid;
    }

    public String getTradestatus() {
        return tradestatus;
    }

    public void setTradestatus(String tradestatus) {
        this.tradestatus = tradestatus;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getReturnparam() {
        return returnparam;
    }

    public void setReturnparam(String returnparam) {
        this.returnparam = returnparam;
    }

    public Long getAddtime() {
        return addtime;
    }

    public void setAddtime(Long addtime) {
        this.addtime = addtime;
    }

    public Long getLasttime() {
        return lasttime;
    }

    public void setLasttime(Long lasttime) {
        this.lasttime = lasttime;
    }
}