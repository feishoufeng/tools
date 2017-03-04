package com.dopakoala.douba.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IUserFeedbackDao;
import com.dopakoala.douba.entity.UserFeedback;
import com.dopakoala.douba.service.IUserFeedbackService;

@Service("userFeedbackService")
public class UserFeedbackServiceImpl implements IUserFeedbackService {

	@Resource
	private IUserFeedbackDao userFeedbackDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.userFeedbackDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(UserFeedback record) {
		// TODO Auto-generated method stub
		this.userFeedbackDao.insertSelective(record);
	}

	@Override
	public UserFeedback selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.userFeedbackDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(UserFeedback record) {
		// TODO Auto-generated method stub
		this.userFeedbackDao.updateByPrimaryKeySelective(record);
	}

}
