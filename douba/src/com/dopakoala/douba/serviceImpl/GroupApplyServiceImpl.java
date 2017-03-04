package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupApplyDao;
import com.dopakoala.douba.entity.GroupApply;
import com.dopakoala.douba.service.IGroupApplyService;

@Service("groupApplyService")
public class GroupApplyServiceImpl implements IGroupApplyService {

	@Resource
	private IGroupApplyDao groupApplyDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupApplyDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupApply record) {
		// TODO Auto-generated method stub
		this.groupApplyDao.insertSelective(record);
	}

	@Override
	public List<GroupApply> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupApply record) {
		// TODO Auto-generated method stub
		this.groupApplyDao.updateByPrimaryKeySelective(record);
	}
}
