package com.dopakoala.douba.entity;

public class OrdersGoodsDetail {
    private Integer id;

    /**
    * 订单号
    */
    private Integer orderId;

    /**
    * 状态
    */
    private Integer status;

    /**
    * 商品名称
    */
    private String goodsname;

    /**
    * 价格
    */
    private Long price;

    /**
    * 数量
    */
    private Integer nums;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }
}