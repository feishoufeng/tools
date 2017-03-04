package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.AppEmoji;

public interface IAppEmojiDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AppEmoji record);

    List<AppEmoji> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppEmoji record);
}