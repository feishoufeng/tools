package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupStat;

public interface IGroupStatService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(GroupStat record);

    public List<GroupStat> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(GroupStat record);
}