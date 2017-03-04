package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.CatalogType;

public interface ICatalogTypeService {
    public int deleteByPrimaryKey(Integer id);

    public int insertSelective(CatalogType record);

    public CatalogType selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(CatalogType record);
    
    public List<CatalogType> selectAllByStatus(Integer status);
}