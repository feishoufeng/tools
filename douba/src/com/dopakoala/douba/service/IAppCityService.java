package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.AppCity;

public interface IAppCityService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(AppCity record);

    public List<AppCity> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(AppCity record);
}