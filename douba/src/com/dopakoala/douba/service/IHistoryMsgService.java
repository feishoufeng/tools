package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.HistoryMsg;

public interface IHistoryMsgService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(HistoryMsg record);

	public HistoryMsg selectByPrimaryKey(Integer id);

	public HistoryMsg selectByMsgId(String msgId);

	public void updateByPrimaryKeySelective(HistoryMsg record);
}