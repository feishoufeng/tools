package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.GroupPace;

public interface IGroupPaceService {
    public int deleteByPrimaryKey(Integer id);

    public int insertSelective(GroupPace record);

    public GroupPace selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(GroupPace record);
    
    public GroupPace selectByGid(Integer gid);
}