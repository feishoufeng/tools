package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.PrivateChat;

public interface IPrivateChatDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(PrivateChat record);

	PrivateChat selectByPrimaryKey(Integer id);

	PrivateChat selectByGidUidToUid(PrivateChat record);

	List<PrivateChat> selectByGidUid(PrivateChat record);

	int updateByPrimaryKeySelective(PrivateChat record);

	int updateStatus(PrivateChat record);
}