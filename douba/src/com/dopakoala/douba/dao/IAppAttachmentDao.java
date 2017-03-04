package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.AppAttachment;

public interface IAppAttachmentDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AppAttachment record);

    List<AppAttachment> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppAttachment record);
}