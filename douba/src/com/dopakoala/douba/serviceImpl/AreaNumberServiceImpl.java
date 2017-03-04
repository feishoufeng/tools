package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAreaNumberDao;
import com.dopakoala.douba.entity.AreaNumber;
import com.dopakoala.douba.service.IAreaNumberService;

@Service("areaNumberService")
public class AreaNumberServiceImpl implements IAreaNumberService {

	@Resource
	private IAreaNumberDao areaNumberDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.areaNumberDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(AreaNumber record) {
		// TODO Auto-generated method stub
		this.areaNumberDao.insertSelective(record);
	}

	@Override
	public AreaNumber selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.areaNumberDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(AreaNumber record) {
		// TODO Auto-generated method stub
		this.areaNumberDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public AreaNumber selectByCode(Integer code) {
		// TODO Auto-generated method stub
		return this.areaNumberDao.selectByCode(code);
	}

	@Override
	public List<AreaNumber> selectByPid(Integer pid) {
		// TODO Auto-generated method stub
		return this.areaNumberDao.selectByPid(pid);
	}

	@Override
	public List<AreaNumber> selectCity() {
		// TODO Auto-generated method stub
		return this.areaNumberDao.selectCity();
	}

	@Override
	public List<AreaNumber> selectArea() {
		// TODO Auto-generated method stub
		return this.areaNumberDao.selectArea();
	}

	@Override
	public List<AreaNumber> selectProvince() {
		// TODO Auto-generated method stub
		return this.areaNumberDao.selectProvince();
	}

}
