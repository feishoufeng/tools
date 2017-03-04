package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAppNewsTypeDao;
import com.dopakoala.douba.entity.AppNewsType;
import com.dopakoala.douba.service.IAppNewsTypeService;

@Service("appNewsTypeService")
public class AppNewsTypeServiceImpl implements IAppNewsTypeService {

	@Resource
	private IAppNewsTypeDao appNewsTypeDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.appNewsTypeDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(AppNewsType record) {
		// TODO Auto-generated method stub
		this.appNewsTypeDao.insertSelective(record);
	}

	@Override
	public AppNewsType selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.appNewsTypeDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(AppNewsType record) {
		// TODO Auto-generated method stub
		this.appNewsTypeDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<AppNewsType> selectAll() {
		// TODO Auto-generated method stub
		return this.appNewsTypeDao.selectAll();
	}

}
