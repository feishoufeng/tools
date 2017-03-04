package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.PayLog;

public interface IPayLogDao {
    int deleteByPrimaryKey(Integer payid);

    int insertSelective(PayLog record);

    PayLog selectByPrimaryKey(Integer payid);

    int updateByPrimaryKeySelective(PayLog record);
}