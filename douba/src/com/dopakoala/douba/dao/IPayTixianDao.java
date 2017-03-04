package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.PayTixian;

public interface IPayTixianDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PayTixian record);

    PayTixian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayTixian record);
    
    List<PayTixian> selectAll(PayTixian record);
}