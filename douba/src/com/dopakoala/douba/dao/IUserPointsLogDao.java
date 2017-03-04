package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.UserPointsLog;

public interface IUserPointsLogDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserPointsLog record);

    List<UserPointsLog> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPointsLog record);
}