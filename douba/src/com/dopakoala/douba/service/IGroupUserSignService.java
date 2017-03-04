package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.GroupUserSign;

public interface IGroupUserSignService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(GroupUserSign record);

	public GroupUserSign selectByPrimaryKey(Integer id);

	public GroupUserSign selectByGidUid(GroupUserSign record);

	public void updateByPrimaryKeySelective(GroupUserSign record);

	public void updateAllByNowmiles();
}