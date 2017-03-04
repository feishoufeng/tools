package com.dopakoala.douba.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.ICatalogTypeDao;
import com.dopakoala.douba.entity.CatalogType;
import com.dopakoala.douba.service.ICatalogTypeService;

@Service("catalogTypeService")
public class CatalogTypeServiceImpl implements ICatalogTypeService {

	@Autowired
	private ICatalogTypeDao catalogTypeDao;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.catalogTypeDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(CatalogType record) {
		// TODO Auto-generated method stub
		return this.catalogTypeDao.insertSelective(record);
	}

	@Override
	public CatalogType selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.catalogTypeDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CatalogType record) {
		// TODO Auto-generated method stub
		return this.catalogTypeDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<CatalogType> selectAllByStatus(Integer status) {
		// TODO Auto-generated method stub
		return this.catalogTypeDao.selectAllByStatus(status);
	}

}
