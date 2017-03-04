package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.ICatalogMenuDao;
import com.dopakoala.douba.entity.CatalogMenu;
import com.dopakoala.douba.service.ICatalogMenuService;

@Service("catalogMenuService")
public class CatalogMenuServiceImpl implements ICatalogMenuService {

	@Resource
	private ICatalogMenuDao catalogMenuDao;

	@Override
	public void deleteByPrimaryKey(Integer menuid) {
		// TODO Auto-generated method stub
		this.catalogMenuDao.deleteByPrimaryKey(menuid);
	}

	@Override
	public void insertSelective(CatalogMenu record) {
		// TODO Auto-generated method stub
		this.catalogMenuDao.insertSelective(record);
	}

	@Override
	public CatalogMenu selectByPrimaryKey(Integer menuid) {
		// TODO Auto-generated method stub
		return this.catalogMenuDao.selectByPrimaryKey(menuid);
	}

	@Override
	public List<CatalogMenu> selectByParentid(CatalogMenu record) {
		// TODO Auto-generated method stub
		return this.catalogMenuDao.selectByParentid(record);
	}

	@Override
	public void updateByPrimaryKeySelective(CatalogMenu record) {
		// TODO Auto-generated method stub
		this.catalogMenuDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<CatalogMenu> selectMainCatalog(CatalogMenu record) {
		// TODO Auto-generated method stub
		return this.catalogMenuDao.selectMainCatalog(record);
	}

}
