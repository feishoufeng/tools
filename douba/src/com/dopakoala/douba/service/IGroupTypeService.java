package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupType;

public interface IGroupTypeService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(GroupType record);

	public GroupType selectByPrimaryKey(Integer id);

	public List<GroupType> selectAll();

	public void updateByPrimaryKeySelective(GroupType record);
}