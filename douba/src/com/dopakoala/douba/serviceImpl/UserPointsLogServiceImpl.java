package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IUserPointsLogDao;
import com.dopakoala.douba.entity.UserPointsLog;
import com.dopakoala.douba.service.IUserPointsLogService;

@Service("userPointsLogService")
public class UserPointsLogServiceImpl implements IUserPointsLogService {

	@Resource
	private IUserPointsLogDao userPointsLogDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.userPointsLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(UserPointsLog record) {
		// TODO Auto-generated method stub
		this.userPointsLogDao.insertSelective(record);
	}

	@Override
	public List<UserPointsLog> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.userPointsLogDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(UserPointsLog record) {
		// TODO Auto-generated method stub
		this.userPointsLogDao.updateByPrimaryKeySelective(record);
	}

}
