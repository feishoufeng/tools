package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IUserVerifyDao;
import com.dopakoala.douba.entity.UserVerify;
import com.dopakoala.douba.service.IUserVerifyService;

@Service("userVerifyService")
public class UserVerifyServiceImpl implements IUserVerifyService {

	@Resource
	private IUserVerifyDao userVerifyDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.userVerifyDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(UserVerify record) {
		// TODO Auto-generated method stub
		this.userVerifyDao.insertSelective(record);
	}

	@Override
	public List<UserVerify> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.userVerifyDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(UserVerify record) {
		// TODO Auto-generated method stub
		this.userVerifyDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<UserVerify> selectByMobile(UserVerify record) {
		// TODO Auto-generated method stub
		return this.userVerifyDao.selectByMobile(record);
	}

}
