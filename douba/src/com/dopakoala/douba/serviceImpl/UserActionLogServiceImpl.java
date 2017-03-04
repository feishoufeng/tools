package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IUserActionLogDao;
import com.dopakoala.douba.entity.UserActionLog;
import com.dopakoala.douba.service.IUserActionLogService;

@Service("userActionLogService")
public class UserActionLogServiceImpl implements IUserActionLogService {

	@Resource
	private IUserActionLogDao userActionLogDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.userActionLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(UserActionLog record) {
		// TODO Auto-generated method stub
		this.userActionLogDao.insertSelective(record);
	}

	@Override
	public List<UserActionLog> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.userActionLogDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(UserActionLog record) {
		// TODO Auto-generated method stub
		this.userActionLogDao.updateByPrimaryKeySelective(record);
	}

}
