package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.OrdersGoodsDetail;

public interface IOrdersGoodsDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrdersGoodsDetail record);

    OrdersGoodsDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrdersGoodsDetail record);
}