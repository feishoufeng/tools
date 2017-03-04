package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.PayTixian;

public interface IPayTixianService {
    public int deleteByPrimaryKey(Integer id);

    public int insertSelective(PayTixian record);

    public PayTixian selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(PayTixian record);
    
    public List<PayTixian> selectAll(PayTixian record);
}