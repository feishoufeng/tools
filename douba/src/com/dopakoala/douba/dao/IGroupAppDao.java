package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupApp;

public interface IGroupAppDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupApp record);

    List<GroupApp> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupApp record);
}