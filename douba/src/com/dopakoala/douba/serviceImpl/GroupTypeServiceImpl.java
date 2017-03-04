package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupTypeDao;
import com.dopakoala.douba.entity.GroupType;
import com.dopakoala.douba.service.IGroupTypeService;

@Service("groupTypeService")
public class GroupTypeServiceImpl implements IGroupTypeService {

	@Resource
	private IGroupTypeDao groupTypeDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupTypeDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupType record) {
		// TODO Auto-generated method stub
		this.insertSelective(record);
	}

	@Override
	public GroupType selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupType record) {
		// TODO Auto-generated method stub
		this.groupTypeDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<GroupType> selectAll() {
		// TODO Auto-generated method stub
		return this.groupTypeDao.selectAll();
	}

}
