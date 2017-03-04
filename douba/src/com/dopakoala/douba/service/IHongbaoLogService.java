package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.HongbaoLog;

public interface IHongbaoLogService {
    public int deleteByPrimaryKey(Integer id);

    public int insertSelective(HongbaoLog record);

    public HongbaoLog selectByPrimaryKey(Integer id);
    
    public HongbaoLog selectByIdUid(HongbaoLog record);

    public int updateByPrimaryKeySelective(HongbaoLog record);
    
    public List<HongbaoLog> selectByHongbaoId(Integer hongbaoId);
}