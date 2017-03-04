package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.AppVersion;

public interface IAppVersionDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(AppVersion record);

	AppVersion selectByPrimaryKey(Integer id);

	AppVersion selectLatest();
	
	AppVersion selectByVersion(Integer version);

	List<AppVersion> selectListByPlatform(AppVersion record);
	
	List<AppVersion> selectByFileName(String filename);

	int updateByPrimaryKeySelective(AppVersion record);
}