package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAppCityDao;
import com.dopakoala.douba.entity.AppCity;
import com.dopakoala.douba.service.IAppCityService;

@Service("appAttachmentService")
public class AppCityServiceImpl implements IAppCityService {

	@Resource
	private IAppCityDao appCityDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.appCityDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(AppCity record) {
		// TODO Auto-generated method stub
		this.appCityDao.insertSelective(record);
	}

	@Override
	public List<AppCity> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.appCityDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(AppCity record) {
		// TODO Auto-generated method stub
		this.appCityDao.updateByPrimaryKeySelective(record);
	}
}
