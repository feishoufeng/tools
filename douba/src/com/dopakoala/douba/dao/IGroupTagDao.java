package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupTag;

public interface IGroupTagDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupTag record);

    List<GroupTag> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupTag record);
}