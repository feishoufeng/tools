package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupUserSignLog;

public interface IGroupUserSignLogService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(GroupUserSignLog record);

	public GroupUserSignLog selectByPrimaryKey(Integer id);

	public List<GroupUserSignLog> selectListByGid(GroupUserSignLog record);
	
	public List<GroupUserSignLog> selectListByUidGidSE(GroupUserSignLog record);

	public GroupUserSignLog selectLastedLogByGid(Integer gid);
	
	public GroupUserSignLog selectTotalByCardId(Integer cardId);
	
	public GroupUserSignLog selectLastedLogByUidGid(GroupUserSignLog record);
	
	public GroupUserSignLog selectLastedLogByUid(Integer uid);

	public GroupUserSignLog selectTotalByGid(Integer gid);
	
	public GroupUserSignLog  selectTotalByUid(Integer uid);
	
	public GroupUserSignLog selectByCardIdUid(GroupUserSignLog record);
	
	public GroupUserSignLog selectStatisticByUid(Integer uid);

	public List<GroupUserSignLog> selectByUidCardId(GroupUserSignLog record);
	
	public List<GroupUserSignLog> selectByCardidSignid(GroupUserSignLog record);
	
	public List<GroupUserSignLog> selectListByCardIdUid(GroupUserSignLog record);

	public void updateByPrimaryKeySelective(GroupUserSignLog record);
	
	public void updateByCardidSignid(GroupUserSignLog record);

	public List<GroupUserSignLog> selectListByCardId(GroupUserSignLog record);
	
	public void updateByCardidSignidRevokemiles(GroupUserSignLog record);
}