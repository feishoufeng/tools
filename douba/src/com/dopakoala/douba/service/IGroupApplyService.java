package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupApply;

public interface IGroupApplyService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(GroupApply record);

    public List<GroupApply> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(GroupApply record);
}