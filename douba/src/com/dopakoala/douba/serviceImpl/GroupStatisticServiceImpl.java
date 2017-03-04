package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupStatisticDao;
import com.dopakoala.douba.entity.GroupStatistic;
import com.dopakoala.douba.service.IGroupStatisticService;

@Service("groupStatisticService")
public class GroupStatisticServiceImpl implements IGroupStatisticService {
	@Resource
	private IGroupStatisticDao groupStatisticDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupStatisticDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupStatistic record) {
		// TODO Auto-generated method stub
		this.groupStatisticDao.insertSelective(record);
	}

	@Override
	public GroupStatistic selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupStatisticDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupStatistic record) {
		// TODO Auto-generated method stub
		this.groupStatisticDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<GroupStatistic> selectByUidGidSidType(GroupStatistic record) {
		// TODO Auto-generated method stub
		return this.groupStatisticDao.selectByUidGidSidType(record);
	}

}
