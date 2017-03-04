package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupTopic;

public interface IGroupTopicService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(GroupTopic record);

	public List<GroupTopic> selectByPrimaryKey(Integer id);

	List<GroupTopic> selectByLimit(Integer gid, Integer page, Integer pagesize, String type);

	public void updateByPrimaryKeySelective(GroupTopic record);
}