package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupAppConfig;

public interface IGroupAppConfigService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(GroupAppConfig record);

    public List<GroupAppConfig> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(GroupAppConfig record);
}