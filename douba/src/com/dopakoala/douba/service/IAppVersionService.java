package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.AppVersion;

public interface IAppVersionService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(AppVersion record);

	public AppVersion selectByPrimaryKey(Integer id);

	public AppVersion selectLatest();
	
	public AppVersion selectByVersion(Integer version);

	public List<AppVersion> selectListByPlatform(AppVersion record);
	
	public List<AppVersion> selectByFileName(String filename);

	public void updateByPrimaryKeySelective(AppVersion record);
}