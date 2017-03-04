package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupAppConfig;

public interface IGroupAppConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupAppConfig record);

    List<GroupAppConfig> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupAppConfig record);
}