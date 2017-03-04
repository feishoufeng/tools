package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.AdminUser;

public interface IAdminUserService {
	public void deleteByPrimaryKey(Integer uid);

	public void insertSelective(AdminUser record);

	public AdminUser selectByPrimaryKey(Integer uid);

	public AdminUser selectByName(String name);

	public AdminUser selectByLoginMsg(AdminUser record);

	public void updateByPrimaryKeySelective(AdminUser record);
}