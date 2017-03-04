package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.AppCity;

public interface IAppCityDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AppCity record);

    List<AppCity> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppCity record);
}