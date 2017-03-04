package com.dopakoala.douba.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IUserMoneyLogDao;
import com.dopakoala.douba.entity.UserMoneyLog;
import com.dopakoala.douba.service.IUserMoneyLogService;

@Service("userMoneyLogService")
public class UserMoneyLogServiceImpl implements IUserMoneyLogService {

	@Autowired
	private IUserMoneyLogDao userMoneyLogDao;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.userMoneyLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(UserMoneyLog record) {
		// TODO Auto-generated method stub
		return this.userMoneyLogDao.insertSelective(record);
	}

	@Override
	public UserMoneyLog selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.userMoneyLogDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserMoneyLog record) {
		// TODO Auto-generated method stub
		return this.userMoneyLogDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<UserMoneyLog> selectAllByUid(UserMoneyLog record) {
		// TODO Auto-generated method stub
		return this.userMoneyLogDao.selectAllByUid(record);
	}

	@Override
	public List<UserMoneyLog> selectAccountByUid(Integer uid) {
		// TODO Auto-generated method stub
		return this.userMoneyLogDao.selectAccountByUid(uid);
	}

}
