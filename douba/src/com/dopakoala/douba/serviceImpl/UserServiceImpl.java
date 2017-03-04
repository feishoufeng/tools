package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IUserDao;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;

	@Override
	public void deleteByPrimaryKey(Integer uid) {
		// TODO Auto-generated method stub
		this.userDao.deleteByPrimaryKey(uid);
	}

	@Override
	public void insertSelective(User record) {
		// TODO Auto-generated method stub
		this.userDao.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(Integer uid) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(uid);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		// TODO Auto-generated method stub
		return this.userDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public User selectByMobile(String mobile) {
		// TODO Auto-generated method stub
		return this.userDao.selectByMobile(mobile);
	}

	@Override
	public User selectByLoginMsg(User record) {
		// TODO Auto-generated method stub
		return this.userDao.selectByLoginMsg(record);
	}

	@Override
	public List<User> selectByGid(Integer gid) {
		// TODO Auto-generated method stub
		return this.userDao.selectByGid(gid);
	}

	@Override
	public List<User> selectAll(User record) {
		// TODO Auto-generated method stub
		return this.userDao.selectAll(record);
	}

	@Override
	public User selectByOpenId(String openid) {
		// TODO Auto-generated method stub
		return this.userDao.selectByOpenId(openid);
	}
}
