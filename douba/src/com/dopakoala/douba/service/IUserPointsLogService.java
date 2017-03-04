package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.UserPointsLog;

public interface IUserPointsLogService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(UserPointsLog record);

    public List<UserPointsLog> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(UserPointsLog record);
}