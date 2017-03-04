package com.dopakoala.douba.service;

import java.util.List;

import com.dopakoala.douba.entity.AppEmoji;

public interface IAppEmojiService {
    public void deleteByPrimaryKey(Integer id);

    public void insertSelective(AppEmoji record);

    public List<AppEmoji> selectByPrimaryKey(Integer id);

    public void updateByPrimaryKeySelective(AppEmoji record);
}