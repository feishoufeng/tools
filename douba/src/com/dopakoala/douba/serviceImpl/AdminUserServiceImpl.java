package com.dopakoala.douba.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAdminUserDao;
import com.dopakoala.douba.entity.AdminUser;
import com.dopakoala.douba.service.IAdminUserService;

@Service("adminUserService")
public class AdminUserServiceImpl implements IAdminUserService {

	@Resource
	private IAdminUserDao adminUserDao;

	@Override
	public void deleteByPrimaryKey(Integer uid) {
		// TODO Auto-generated method stub
		this.adminUserDao.deleteByPrimaryKey(uid);
	}

	@Override
	public void insertSelective(AdminUser record) {
		// TODO Auto-generated method stub
		this.adminUserDao.insertSelective(record);
	}

	@Override
	public AdminUser selectByPrimaryKey(Integer uid) {
		// TODO Auto-generated method stub
		return this.selectByPrimaryKey(uid);
	}

	@Override
	public AdminUser selectByLoginMsg(AdminUser record) {
		// TODO Auto-generated method stub
		return this.adminUserDao.selectByLoginMsg(record);
	}

	@Override
	public void updateByPrimaryKeySelective(AdminUser record) {
		// TODO Auto-generated method stub
		this.adminUserDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public AdminUser selectByName(String name) {
		// TODO Auto-generated method stub
		return this.adminUserDao.selectByName(name);
	}

}
