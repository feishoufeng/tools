package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.AppNew;

public interface IAppNewDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AppNew record);

    AppNew selectByPrimaryKey(Integer id);
    
    List<AppNew> selectAll(AppNew record);

    int updateByPrimaryKeySelective(AppNew record);
}