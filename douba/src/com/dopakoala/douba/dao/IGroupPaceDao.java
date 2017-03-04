package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.GroupPace;

public interface IGroupPaceDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupPace record);

    GroupPace selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupPace record);
    
    GroupPace selectByGid(Integer gid);
}