package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.HistoryMsg;

public interface IHistoryMsgDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(HistoryMsg record);

	HistoryMsg selectByPrimaryKey(Integer id);

	HistoryMsg selectByMsgId(String msgId);

	int updateByPrimaryKeySelective(HistoryMsg record);

	int updateByPrimaryKeyWithBLOBs(HistoryMsg record);
}