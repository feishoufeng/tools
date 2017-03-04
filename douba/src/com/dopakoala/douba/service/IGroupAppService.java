package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupApp;

public interface IGroupAppService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(GroupApp record);

    public List<GroupApp> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(GroupApp record);
}