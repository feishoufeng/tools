package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.UserActionLog;

public interface IUserActionLogService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(UserActionLog record);

    public List<UserActionLog> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(UserActionLog record);
}