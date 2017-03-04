package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupApply;

public interface IGroupApplyDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupApply record);

    List<GroupApply> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupApply record);
}