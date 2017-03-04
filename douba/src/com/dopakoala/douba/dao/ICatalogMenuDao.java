package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.CatalogMenu;

public interface ICatalogMenuDao {
	int deleteByPrimaryKey(Integer menuid);

	int insertSelective(CatalogMenu record);

	CatalogMenu selectByPrimaryKey(Integer menuid);

	List<CatalogMenu> selectByParentid(CatalogMenu record);
	
	List<CatalogMenu> selectMainCatalog(CatalogMenu record);

	int updateByPrimaryKeySelective(CatalogMenu record);
}