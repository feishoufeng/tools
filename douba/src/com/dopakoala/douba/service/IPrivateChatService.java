package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.PrivateChat;

public interface IPrivateChatService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(PrivateChat record);

	public PrivateChat selectByPrimaryKey(Integer id);

	public PrivateChat selectByGidUidToUid(PrivateChat record);

	public List<PrivateChat> selectByGidUid(PrivateChat record);

	public void updateByPrimaryKeySelective(PrivateChat record);

	public void updateStatus(PrivateChat record);
}