package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupUser;

public interface IGroupUserDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(GroupUser record);

	GroupUser selectByPrimaryKey(Integer id);

	List<GroupUser> selectByGid(GroupUser record);

	List<GroupUser> selectTopUserInfo(GroupUser record);
	
	List<GroupUser> selectAllOpen();

	GroupUser selectByUidGid(GroupUser record);

	GroupUser selectOpenByGid(Integer gid);
	
	GroupUser selectSuperByGid(Integer gid);

	List<GroupUser> selectAdminNumByGid(Integer gid);

	List<GroupUser> selectAdminByGid(Integer gid);

	List<GroupUser> selectNumByUid(Integer uid);

	int updateByPrimaryKeySelective(GroupUser record);

	int updateByGidSelective(GroupUser record);
}