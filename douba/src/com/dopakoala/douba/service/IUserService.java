package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.User;

public interface IUserService {
    public void deleteByPrimaryKey(Integer uid);

    public void insertSelective(User record);

    public User selectByPrimaryKey(Integer uid);
    
    public List<User> selectByGid(Integer gid);
    
    public User selectByOpenId(String openid);
    
    public User selectByMobile(String mobile);
    
    public User selectByLoginMsg(User record);
    
    public List<User> selectAll(User record);

    public int updateByPrimaryKeySelective(User record);
}