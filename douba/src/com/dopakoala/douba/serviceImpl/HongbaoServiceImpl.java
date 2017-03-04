package com.dopakoala.douba.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IHongbaoDao;
import com.dopakoala.douba.entity.Hongbao;
import com.dopakoala.douba.service.IHongbaoService;

@Service("hongbaoService")
public class HongbaoServiceImpl implements IHongbaoService {

	@Autowired
	private IHongbaoDao hongbaoDao;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.hongbaoDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(Hongbao record) {
		// TODO Auto-generated method stub
		return this.hongbaoDao.insertSelective(record);
	}

	@Override
	public Hongbao selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.hongbaoDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Hongbao record) {
		// TODO Auto-generated method stub
		return this.hongbaoDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Hongbao> selectAllByUid(Hongbao record) {
		// TODO Auto-generated method stub
		return this.hongbaoDao.selectAllByUid(record);
	}

}
