package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.GroupUserSign;

public interface IGroupUserSignDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(GroupUserSign record);

	GroupUserSign selectByPrimaryKey(Integer id);

	GroupUserSign selectByGidUid(GroupUserSign record);

	int updateByPrimaryKeySelective(GroupUserSign record);

	int updateAllByNowmiles();
}