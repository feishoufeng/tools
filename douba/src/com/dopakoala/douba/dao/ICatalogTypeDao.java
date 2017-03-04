package com.dopakoala.douba.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dopakoala.douba.entity.CatalogType;

public interface ICatalogTypeDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(CatalogType record);

    CatalogType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CatalogType record);
    
    List<CatalogType> selectAllByStatus(@Param("status") Integer status);
}