package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupLevel;

public interface IGroupLevelDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupLevel record);

    List<GroupLevel> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupLevel record);
}