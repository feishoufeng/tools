package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupTagDao;
import com.dopakoala.douba.entity.GroupTag;
import com.dopakoala.douba.service.IGroupTagService;

@Service("groupTagService")
public class GroupTagServiceImpl implements IGroupTagService {

	@Resource
	private IGroupTagDao groupTagDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupTagDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupTag record) {
		// TODO Auto-generated method stub
		this.groupTagDao.insertSelective(record);
	}

	@Override
	public List<GroupTag> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupTagDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupTag record) {
		// TODO Auto-generated method stub
		this.groupTagDao.updateByPrimaryKeySelective(record);
	}

}
