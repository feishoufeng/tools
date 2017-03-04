package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.GroupPhoto;

public interface IGroupPhotoService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(GroupPhoto record);

    public List<GroupPhoto> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(GroupPhoto record);
}