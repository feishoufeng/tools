package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.AppConfig;

public interface IAppConfigDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(AppConfig record);

	AppConfig selectByPrimaryKey(Integer id);

	AppConfig selectByName(String name);

	int updateByPrimaryKeySelective(AppConfig record);
}