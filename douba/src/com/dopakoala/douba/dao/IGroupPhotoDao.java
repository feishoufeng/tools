package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.GroupPhoto;

public interface IGroupPhotoDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupPhoto record);

    List<GroupPhoto> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupPhoto record);
}