package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.Group;

public interface ISimpleGroupDao {
	int deleteByPrimaryKey(Integer gid);

	int insertSelective(Group record);

	Group selectByPrimaryKey(Group record);

	List<Group> selectByPidUid(Group record);

	List<Group> selectTypeNameByKeyword(Group record);

	List<Group> selectMainByPid(Integer pid);

	int updateByPrimaryKeySelective(Group record);
}