package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupLevel;

public interface IGroupLevelService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(GroupLevel record);

    public List<GroupLevel> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(GroupLevel record);
}