package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAppVersionDao;
import com.dopakoala.douba.entity.AppVersion;
import com.dopakoala.douba.service.IAppVersionService;

@Service("appVersionService")
public class AppVersionServiceImpl implements IAppVersionService {

	@Resource
	private IAppVersionDao appVersionDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.appVersionDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(AppVersion record) {
		// TODO Auto-generated method stub
		this.appVersionDao.insertSelective(record);
	}

	@Override
	public AppVersion selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.appVersionDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(AppVersion record) {
		// TODO Auto-generated method stub
		this.appVersionDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public AppVersion selectLatest() {
		// TODO Auto-generated method stub
		return this.appVersionDao.selectLatest();
	}

	@Override
	public List<AppVersion> selectListByPlatform(AppVersion record) {
		// TODO Auto-generated method stub
		return this.appVersionDao.selectListByPlatform(record);
	}

	@Override
	public List<AppVersion> selectByFileName(String filename) {
		// TODO Auto-generated method stub
		return this.appVersionDao.selectByFileName(filename);
	}

	@Override
	public AppVersion selectByVersion(Integer version) {
		// TODO Auto-generated method stub
		return this.appVersionDao.selectByVersion(version);
	}

}
