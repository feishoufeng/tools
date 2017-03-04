package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.UserFriend;

public interface IUserFriendDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserFriend record);

    List<UserFriend> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFriend record);
}