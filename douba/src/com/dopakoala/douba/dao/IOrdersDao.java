package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.Orders;

public interface IOrdersDao {
    int deleteByPrimaryKey(Integer orderId);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Orders record);
}