package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupStat;

public interface IGroupStatDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupStat record);

    List<GroupStat> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupStat record);
}