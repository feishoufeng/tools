package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupStatistic;

public interface IGroupStatisticService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(GroupStatistic record);

	public GroupStatistic selectByPrimaryKey(Integer id);

	public List<GroupStatistic> selectByUidGidSidType(GroupStatistic record);

	public void updateByPrimaryKeySelective(GroupStatistic record);
}