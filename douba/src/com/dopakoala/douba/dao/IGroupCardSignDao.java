package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.GroupCardSign;

public interface IGroupCardSignDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupCardSign record);

    GroupCardSign selectByPrimaryKey(Integer id);
    
    GroupCardSign selectLasted(Integer gid);
    
    GroupCardSign selectByTimeGid(GroupCardSign record);

    int updateByPrimaryKeySelective(GroupCardSign record);
}