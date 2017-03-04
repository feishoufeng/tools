package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupUserSignLog;

public interface IGroupUserSignLogDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(GroupUserSignLog record);

	GroupUserSignLog selectByPrimaryKey(Integer id);

	List<GroupUserSignLog> selectListByGid(GroupUserSignLog record);

	List<GroupUserSignLog> selectListByUidGidSE(GroupUserSignLog record);

	GroupUserSignLog selectLastedLogByGid(Integer gid);
	
	GroupUserSignLog selectTotalByCardId(Integer cardId);
	
	GroupUserSignLog selectLastedLogByUidGid(GroupUserSignLog record);
	
	GroupUserSignLog selectLastedLogByUid(Integer uid);

	GroupUserSignLog selectTotalByGid(Integer gid);
	
	GroupUserSignLog  selectTotalByUid(Integer uid);
	
	GroupUserSignLog selectByCardIdUid(GroupUserSignLog record);
	
	GroupUserSignLog selectStatisticByUid(Integer uid);

	List<GroupUserSignLog> selectByUidCardId(GroupUserSignLog record);
	
	List<GroupUserSignLog> selectListByCardId(GroupUserSignLog record);
	
	List<GroupUserSignLog> selectByCardidSignid(GroupUserSignLog record);
	
	List<GroupUserSignLog> selectListByCardIdUid(GroupUserSignLog record);

	int updateByPrimaryKeySelective(GroupUserSignLog record);
	
	int updateByCardidSignid(GroupUserSignLog record);
	
	int updateByCardidSignidRevokemiles(GroupUserSignLog record);
}