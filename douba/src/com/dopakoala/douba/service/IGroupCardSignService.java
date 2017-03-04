package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.GroupCardSign;

public interface IGroupCardSignService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(GroupCardSign record);

	public GroupCardSign selectByPrimaryKey(Integer id);

	public GroupCardSign selectLasted(Integer gid);

	public GroupCardSign selectByTimeGid(GroupCardSign record);

	public void updateByPrimaryKeySelective(GroupCardSign record);
}