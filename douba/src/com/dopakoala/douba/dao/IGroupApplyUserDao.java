package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupApplyUser;

public interface IGroupApplyUserDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupApplyUser record);

    GroupApplyUser selectByPrimaryKey(Integer id);
    
    List<GroupApplyUser> selectByGid(GroupApplyUser record);
    
    GroupApplyUser selectByGidUid(GroupApplyUser record);
    
    List<GroupApplyUser> selectByUid(Integer uid);
    
    List<GroupApplyUser> selectCheckByGidUid(GroupApplyUser record);

    int updateByPrimaryKeySelective(GroupApplyUser record);
}