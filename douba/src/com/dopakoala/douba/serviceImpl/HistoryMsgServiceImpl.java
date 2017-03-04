package com.dopakoala.douba.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IHistoryMsgDao;
import com.dopakoala.douba.entity.HistoryMsg;
import com.dopakoala.douba.service.IHistoryMsgService;

@Service("historyMsgService")
public class HistoryMsgServiceImpl implements IHistoryMsgService {
	@Resource
	private IHistoryMsgDao historyMsgDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.historyMsgDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(HistoryMsg record) {
		// TODO Auto-generated method stub
		this.historyMsgDao.insertSelective(record);
	}

	@Override
	public HistoryMsg selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.historyMsgDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(HistoryMsg record) {
		// TODO Auto-generated method stub
		this.historyMsgDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public HistoryMsg selectByMsgId(String msgId) {
		// TODO Auto-generated method stub
		return this.historyMsgDao.selectByMsgId(msgId);
	}

}
