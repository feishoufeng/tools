package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.AppNewsType;

public interface IAppNewsTypeDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AppNewsType record);

    AppNewsType selectByPrimaryKey(Integer id);
    
    List<AppNewsType> selectAll();

    int updateByPrimaryKeySelective(AppNewsType record);
}