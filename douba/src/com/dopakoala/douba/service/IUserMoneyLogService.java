package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.UserMoneyLog;

public interface IUserMoneyLogService {
    public int deleteByPrimaryKey(Integer id);

    public int insertSelective(UserMoneyLog record);

    public UserMoneyLog selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(UserMoneyLog record);
    
    public List<UserMoneyLog> selectAllByUid(UserMoneyLog record);
    
    public List<UserMoneyLog> selectAccountByUid(Integer uid);
}