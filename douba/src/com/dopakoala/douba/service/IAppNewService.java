package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.AppNew;

public interface IAppNewService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(AppNew record);

    public AppNew selectByPrimaryKey(Integer id); 
    
    public List<AppNew> selectAll(AppNew record);

    public void updateByPrimaryKeySelective(AppNew record);
}