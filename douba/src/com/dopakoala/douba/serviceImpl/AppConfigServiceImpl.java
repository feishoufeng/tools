package com.dopakoala.douba.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAppConfigDao;
import com.dopakoala.douba.entity.AppConfig;
import com.dopakoala.douba.service.IAppConfigService;

@Service("appConfigService")
public class AppConfigServiceImpl implements IAppConfigService {

	@Resource
	private IAppConfigDao appConfigDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.appConfigDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(AppConfig record) {
		// TODO Auto-generated method stub
		this.appConfigDao.insertSelective(record);
	}

	@Override
	public AppConfig selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.appConfigDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(AppConfig record) {
		// TODO Auto-generated method stub
		this.appConfigDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public AppConfig selectByName(String name) {
		// TODO Auto-generated method stub
		return this.appConfigDao.selectByName(name);
	}

}
