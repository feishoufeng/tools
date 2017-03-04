package com.dopakoala.douba.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IHongbaoLogDao;
import com.dopakoala.douba.entity.HongbaoLog;
import com.dopakoala.douba.service.IHongbaoLogService;

@Service("hongbaoLogService")
public class HongbaoLogServiceImpl implements IHongbaoLogService {

	@Autowired
	private IHongbaoLogDao hongbaoLogDao;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.hongbaoLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(HongbaoLog record) {
		// TODO Auto-generated method stub
		return this.hongbaoLogDao.insertSelective(record);
	}

	@Override
	public HongbaoLog selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.hongbaoLogDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(HongbaoLog record) {
		// TODO Auto-generated method stub
		return this.hongbaoLogDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<HongbaoLog> selectByHongbaoId(Integer hongbaoId) {
		// TODO Auto-generated method stub
		return this.hongbaoLogDao.selectByHongbaoId(hongbaoId);
	}

	@Override
	public HongbaoLog selectByIdUid(HongbaoLog record) {
		// TODO Auto-generated method stub
		return this.hongbaoLogDao.selectByIdUid(record);
	}

}
