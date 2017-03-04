package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.Hongbao;

public interface IHongbaoService {
    public int deleteByPrimaryKey(Integer id);

    public int insertSelective(Hongbao record);

    public Hongbao selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(Hongbao record);
    
    public List<Hongbao> selectAllByUid(Hongbao record);
}