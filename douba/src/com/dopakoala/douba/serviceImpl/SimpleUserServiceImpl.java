package com.dopakoala.douba.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.ISimpleUserDao;
import com.dopakoala.douba.entity.SimpleUser;
import com.dopakoala.douba.service.ISimpleUserService;

@Service("simpleUserService")
public class SimpleUserServiceImpl implements ISimpleUserService {

	@Resource
	private ISimpleUserDao simpleUserDao; 
	
	@Override
	public SimpleUser selectUser(Integer uid, Integer gid) {
		// TODO Auto-generated method stub
		return this.simpleUserDao.selectUser(uid, gid);
	}

	@Override
	public SimpleUser selectUserByUid(Integer uid) {
		// TODO Auto-generated method stub
		return this.simpleUserDao.selectUserByUid(uid);
	}

}
