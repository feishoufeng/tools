package com.dopakoala.douba.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dopakoala.douba.entity.GroupTopic;

public interface IGroupTopicDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(GroupTopic record);

	List<GroupTopic> selectByPrimaryKey(Integer id);

	List<GroupTopic> selectByLimit(@Param("gid") Integer gid, @Param("page") Integer page,
			@Param("pagesize") Integer pagesize, @Param("type") String type);

	int updateByPrimaryKeySelective(GroupTopic record);
}