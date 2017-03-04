package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupApplyUser;

public interface IGroupApplyUserService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(GroupApplyUser record);

    public GroupApplyUser selectByPrimaryKey(Integer id);
    
    public List<GroupApplyUser> selectByGid(GroupApplyUser record);
    
    public GroupApplyUser selectByGidUid(GroupApplyUser record);
    
    public List<GroupApplyUser> selectByUid(Integer uid);

    public void updateByPrimaryKeySelective(GroupApplyUser record);

    public List<GroupApplyUser> selectCheckByGidUid(GroupApplyUser record);
}