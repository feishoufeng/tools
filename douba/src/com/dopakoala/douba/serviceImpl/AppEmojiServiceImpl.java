package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAppEmojiDao;
import com.dopakoala.douba.entity.AppEmoji;
import com.dopakoala.douba.service.IAppEmojiService;

@Service("appEmojiService")
public class AppEmojiServiceImpl implements IAppEmojiService {

	@Resource
	private IAppEmojiDao appEmojiDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.appEmojiDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(AppEmoji record) {
		// TODO Auto-generated method stub
		this.appEmojiDao.insertSelective(record);
	}

	@Override
	public List<AppEmoji> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.appEmojiDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(AppEmoji record) {
		// TODO Auto-generated method stub
		this.appEmojiDao.updateByPrimaryKeySelective(record);
	}
}
