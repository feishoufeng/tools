package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAppNewDao;
import com.dopakoala.douba.entity.AppNew;
import com.dopakoala.douba.service.IAppNewService;

@Service("appNewService")
public class AppNewServiceImpl implements IAppNewService {

	@Resource
	private IAppNewDao appNewDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.appNewDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(AppNew record) {
		// TODO Auto-generated method stub
		this.appNewDao.insertSelective(record);
	}

	@Override
	public AppNew selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.appNewDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(AppNew record) {
		// TODO Auto-generated method stub
		this.appNewDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<AppNew> selectAll(AppNew record) {
		// TODO Auto-generated method stub
		return this.appNewDao.selectAll(record);
	}

}
