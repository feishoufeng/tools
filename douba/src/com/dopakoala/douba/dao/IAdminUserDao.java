package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.AdminUser;

public interface IAdminUserDao {
    int deleteByPrimaryKey(Integer uid);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer uid);
    
    AdminUser selectByName(String name);
    
    AdminUser selectByLoginMsg(AdminUser record);

    int updateByPrimaryKeySelective(AdminUser record);
}