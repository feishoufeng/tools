package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.UserVerify;

public interface IUserVerifyService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(UserVerify record);

    public List<UserVerify> selectByPrimaryKey(Integer id);
    
    public List<UserVerify> selectByMobile(UserVerify record);

    public void updateByPrimaryKeySelective(UserVerify record);
}