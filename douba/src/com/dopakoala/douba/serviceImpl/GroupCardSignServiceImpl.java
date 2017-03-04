package com.dopakoala.douba.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupCardSignDao;
import com.dopakoala.douba.entity.GroupCardSign;
import com.dopakoala.douba.service.IGroupCardSignService;

@Service("groupCardSignService")
public class GroupCardSignServiceImpl implements IGroupCardSignService {

	@Resource
	private IGroupCardSignDao groupCardSignDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupCardSignDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupCardSign record) {
		// TODO Auto-generated method stub
		this.groupCardSignDao.insertSelective(record);
	}

	@Override
	public GroupCardSign selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupCardSignDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupCardSign record) {
		// TODO Auto-generated method stub
		this.groupCardSignDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public GroupCardSign selectLasted(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupCardSignDao.selectLasted(gid);
	}

	@Override
	public GroupCardSign selectByTimeGid(GroupCardSign record) {
		// TODO Auto-generated method stub
		return this.groupCardSignDao.selectByTimeGid(record);
	}
}
