package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.AppAttachment;

public interface IAppAttachmentService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(AppAttachment record);

    public List<AppAttachment> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(AppAttachment record);
}