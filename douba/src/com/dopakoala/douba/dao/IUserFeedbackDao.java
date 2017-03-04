package com.dopakoala.douba.dao;

import com.dopakoala.douba.entity.UserFeedback;

public interface IUserFeedbackDao {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(UserFeedback record);

	UserFeedback selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserFeedback record);
}