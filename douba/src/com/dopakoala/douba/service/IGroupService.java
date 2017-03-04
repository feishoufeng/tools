package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.Group;

public interface IGroupService {
	public void deleteByPrimaryKey(Integer gid);

	public void insertSelective(Group record);

	public Group selectByUidGid(Group record);

	public Group selectByPrimaryKey(Integer gid);

	public List<Group> selectTypeNameByPid(Group record);
	
	public List<Group> selectByPidUid(Group record);

	public List<Group> selectTypeNameByKeyword(Group record);

	public List<Group> selectGroupByUid(Group record);

	public List<Group> selectPaotuanByUid(Integer uid);
	
	public List<Group> selectPaoTuan(Group record);
	
	public List<Group> selectGroup(Group record);

	public Group selectMainByPid(Integer pid);

	public Group selectTempByPid(Integer pid);

	public void updateByPrimaryKeySelective(Group record);

	public void updateQuitByPid(Integer pid);
}