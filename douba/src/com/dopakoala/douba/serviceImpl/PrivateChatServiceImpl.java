package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IPrivateChatDao;
import com.dopakoala.douba.entity.PrivateChat;
import com.dopakoala.douba.service.IPrivateChatService;

@Service("privateChatService")
public class PrivateChatServiceImpl implements IPrivateChatService {
	@Resource
	private IPrivateChatDao privateChatDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.privateChatDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(PrivateChat record) {
		// TODO Auto-generated method stub
		this.privateChatDao.insertSelective(record);
	}

	@Override
	public PrivateChat selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.privateChatDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(PrivateChat record) {
		// TODO Auto-generated method stub
		this.privateChatDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<PrivateChat> selectByGidUid(PrivateChat record) {
		// TODO Auto-generated method stub
		return this.privateChatDao.selectByGidUid(record);
	}

	@Override
	public void updateStatus(PrivateChat record) {
		// TODO Auto-generated method stub
		this.privateChatDao.updateStatus(record);
	}

	@Override
	public PrivateChat selectByGidUidToUid(PrivateChat record) {
		// TODO Auto-generated method stub
		return this.privateChatDao.selectByGidUidToUid(record);
	}

}
