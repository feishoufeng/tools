package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.HongbaoLog;

public interface IHongbaoLogDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(HongbaoLog record);

	HongbaoLog selectByPrimaryKey(Integer id);
	
	HongbaoLog selectByIdUid(HongbaoLog record);

	int updateByPrimaryKeySelective(HongbaoLog record);

	List<HongbaoLog> selectByHongbaoId(Integer hongbaoId);
}