package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.UserFriend;

public interface IUserFriendService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(UserFriend record);

    public List<UserFriend> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(UserFriend record);
}