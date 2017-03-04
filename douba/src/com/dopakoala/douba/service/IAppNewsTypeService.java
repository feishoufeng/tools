package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.AppNewsType;

public interface IAppNewsTypeService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(AppNewsType record);

    public AppNewsType selectByPrimaryKey(Integer id);
    
    public List<AppNewsType> selectAll();

    public void updateByPrimaryKeySelective(AppNewsType record);
}