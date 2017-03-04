package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupStatDao;
import com.dopakoala.douba.entity.GroupStat;
import com.dopakoala.douba.service.IGroupStatService;

@Service("groupStatService")
public class GroupStatServiceImpl implements IGroupStatService {

	@Resource
	private IGroupStatDao groupStatDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupStatDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupStat record) {
		// TODO Auto-generated method stub
		this.groupStatDao.insertSelective(record);
	}

	@Override
	public List<GroupStat> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupStatDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupStat record) {
		// TODO Auto-generated method stub
		this.groupStatDao.updateByPrimaryKeySelective(record);
	}

}
