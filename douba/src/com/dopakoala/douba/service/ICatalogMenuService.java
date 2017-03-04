package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.CatalogMenu;

public interface ICatalogMenuService {
	public void deleteByPrimaryKey(Integer menuid);

	public void insertSelective(CatalogMenu record);

	public CatalogMenu selectByPrimaryKey(Integer menuid);

	public List<CatalogMenu> selectByParentid(CatalogMenu record);
	
	public List<CatalogMenu> selectMainCatalog(CatalogMenu record);

	public void updateByPrimaryKeySelective(CatalogMenu record);
}