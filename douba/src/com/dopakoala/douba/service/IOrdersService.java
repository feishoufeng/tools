package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.Orders;

public interface IOrdersService {
    public int deleteByPrimaryKey(Integer orderId);

    public int insertSelective(Orders record);

    public Orders selectByPrimaryKey(Integer orderId);

    public int updateByPrimaryKeySelective(Orders record);
}