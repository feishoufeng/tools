package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.UserVerify;

public interface IUserVerifyDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserVerify record);

    List<UserVerify> selectByPrimaryKey(Integer id);
    
    List<UserVerify> selectByMobile(UserVerify record);

    int updateByPrimaryKeySelective(UserVerify record);
}