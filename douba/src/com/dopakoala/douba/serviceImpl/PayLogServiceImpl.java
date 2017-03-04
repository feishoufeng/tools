package com.dopakoala.douba.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IPayLogDao;
import com.dopakoala.douba.entity.PayLog;
import com.dopakoala.douba.service.IPayLogService;

@Service("payLogService")
public class PayLogServiceImpl implements IPayLogService {
	
	@Autowired
	private IPayLogDao payLogDao;
	
	@Override
	public int deleteByPrimaryKey(int payid) {
		// TODO Auto-generated method stub
		return this.payLogDao.deleteByPrimaryKey(payid);
	}

	@Override
	public int insertSelective(PayLog record) {
		// TODO Auto-generated method stub
		return this.payLogDao.insertSelective(record);
	}

	@Override
	public PayLog selectByPrimaryKey(int payid) {
		// TODO Auto-generated method stub
		return this.payLogDao.selectByPrimaryKey(payid);
	}

	@Override
	public int updateByPrimaryKeySelective(PayLog record) {
		// TODO Auto-generated method stub
		return this.payLogDao.updateByPrimaryKeySelective(record);
	}

}
