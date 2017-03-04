package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupType;

public interface IGroupTypeDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(GroupType record);

	GroupType selectByPrimaryKey(Integer id);

	List<GroupType> selectAll();

	int updateByPrimaryKeySelective(GroupType record);
}