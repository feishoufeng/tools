package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.UserMoneyLog;

public interface IUserMoneyLogDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserMoneyLog record);

    UserMoneyLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserMoneyLog record);
    
    List<UserMoneyLog> selectAllByUid(UserMoneyLog record);
    
    List<UserMoneyLog> selectAccountByUid(Integer uid);
}