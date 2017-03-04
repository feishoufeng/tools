package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.UserFeedback;

public interface IUserFeedbackService {
	public void deleteByPrimaryKey(Integer id);

	public void insertSelective(UserFeedback record);

	public UserFeedback selectByPrimaryKey(Integer id);

	public void updateByPrimaryKeySelective(UserFeedback record);
}