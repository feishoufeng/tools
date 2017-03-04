package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupStatistic;

public interface IGroupStatisticDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(GroupStatistic record);

	GroupStatistic selectByPrimaryKey(Integer id);

	List<GroupStatistic> selectByUidGidSidType(GroupStatistic record);

	int updateByPrimaryKeySelective(GroupStatistic record);
}