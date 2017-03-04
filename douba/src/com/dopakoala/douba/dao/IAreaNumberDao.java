package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.AreaNumber;

public interface IAreaNumberDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(AreaNumber record);

	AreaNumber selectByPrimaryKey(Integer id);

	AreaNumber selectByCode(Integer code);

	List<AreaNumber> selectByPid(Integer pid);

	List<AreaNumber> selectCity();

	List<AreaNumber> selectArea();

	List<AreaNumber> selectProvince();

	int updateByPrimaryKeySelective(AreaNumber record);
}