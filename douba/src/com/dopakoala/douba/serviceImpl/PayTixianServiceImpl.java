package com.dopakoala.douba.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IPayTixianDao;
import com.dopakoala.douba.entity.PayTixian;
import com.dopakoala.douba.service.IPayTixianService;

@Service("payTixianService")
public class PayTixianServiceImpl implements IPayTixianService {

	@Autowired
	private IPayTixianDao payTixianDao;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.payTixianDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(PayTixian record) {
		// TODO Auto-generated method stub
		return this.payTixianDao.insertSelective(record);
	}

	@Override
	public PayTixian selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.payTixianDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(PayTixian record) {
		// TODO Auto-generated method stub
		return this.payTixianDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<PayTixian> selectAll(PayTixian record) {
		// TODO Auto-generated method stub
		return this.payTixianDao.selectAll(record);
	}

}
