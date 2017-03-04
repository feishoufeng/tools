package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.Group;

public interface IGroupDao {
	int deleteByPrimaryKey(Integer gid);

	int insertSelective(Group record);

	Group selectByUidGid(Group record);

	Group selectByPrimaryKey(Integer gid);

	List<Group> selectTypeNameByPid(Group record);
	
	List<Group> selectByPidUid(Group record);

	List<Group> selectTypeNameByKeyword(Group record);

	List<Group> selectGroupByUid(Group record);

	List<Group> selectPaotuanByUid(Integer uid);
	
	List<Group> selectPaoTuan(Group record);
	
	List<Group> selectGroup(Group record);

	Group selectMainByPid(Integer pid);

	Group selectTempByPid(Integer pid);

	int updateByPrimaryKeySelective(Group record);

	int updateQuitByPid(Integer pid);
}