package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupUser;

public interface IGroupUserService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(GroupUser record);

	public GroupUser selectByPrimaryKey(Integer id);

	public List<GroupUser> selectByGid(GroupUser record);

	public List<GroupUser> selectTopUserInfo(GroupUser record);

	public GroupUser selectByUidGid(GroupUser record);

	public GroupUser selectOpenByGid(Integer gid);
	
	public GroupUser selectSuperByGid(Integer gid);

	public List<GroupUser> selectAdminByGid(Integer gid);

	public List<GroupUser> selectAdminNumByGid(Integer gid);

	public List<GroupUser> selectNumByUid(Integer uid);

	public void updateByPrimaryKeySelective(GroupUser record);

	public void updateByGidSelective(GroupUser record);
	
	public List<GroupUser> selectAllOpen();
}