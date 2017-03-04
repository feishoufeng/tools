package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupTag;

public interface IGroupTagService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(GroupTag record);

    public List<GroupTag> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(GroupTag record);
}