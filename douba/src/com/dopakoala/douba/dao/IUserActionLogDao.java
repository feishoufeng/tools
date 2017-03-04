package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.UserActionLog;

public interface IUserActionLogDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserActionLog record);

    List<UserActionLog> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserActionLog record);
}