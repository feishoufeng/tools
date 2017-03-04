package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IUserFriendDao;
import com.dopakoala.douba.entity.UserFriend;
import com.dopakoala.douba.service.IUserFriendService;

@Service("userFriendService")
public class UserFriendServiceImpl implements IUserFriendService{

	@Resource
	private IUserFriendDao userFriendDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.userFriendDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(UserFriend record) {
		// TODO Auto-generated method stub
		this.userFriendDao.insertSelective(record);
	}

	@Override
	public List<UserFriend> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.userFriendDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(UserFriend record) {
		// TODO Auto-generated method stub
		this.userFriendDao.updateByPrimaryKeySelective(record);
	}

}
