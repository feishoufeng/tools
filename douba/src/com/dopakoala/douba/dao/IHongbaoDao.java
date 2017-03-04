package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.Hongbao;

public interface IHongbaoDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Hongbao record);

    Hongbao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hongbao record);
    
    List<Hongbao> selectAllByUid(Hongbao record);
}