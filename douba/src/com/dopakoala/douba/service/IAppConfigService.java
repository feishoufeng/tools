package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.AppConfig;

public interface IAppConfigService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(AppConfig record);

    public AppConfig selectByPrimaryKey(Integer id);
    
    public AppConfig selectByName(String name);

    public void updateByPrimaryKeySelective(AppConfig record);
}