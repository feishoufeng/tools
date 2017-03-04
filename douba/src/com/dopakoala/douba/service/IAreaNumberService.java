package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.AreaNumber;

public interface IAreaNumberService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(AreaNumber record);

	public AreaNumber selectByPrimaryKey(Integer id);

	public AreaNumber selectByCode(Integer code);
	
	public List<AreaNumber> selectCity();
	
	public List<AreaNumber> selectArea();
	
	public List<AreaNumber> selectProvince();

	public List<AreaNumber> selectByPid(Integer pid);

	public void updateByPrimaryKeySelective(AreaNumber record);
}