package com.dopakoala.douba.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupUserSignDao;
import com.dopakoala.douba.entity.GroupUserSign;
import com.dopakoala.douba.service.IGroupUserSignService;

@Service("groupUserSignService")
public class GroupUserSignServiceImpl implements IGroupUserSignService {

	@Resource
	private IGroupUserSignDao groupUserSignDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupUserSignDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupUserSign record) {
		// TODO Auto-generated method stub
		this.groupUserSignDao.insertSelective(record);
	}

	@Override
	public GroupUserSign selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupUserSignDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupUserSign record) {
		// TODO Auto-generated method stub
		this.groupUserSignDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public GroupUserSign selectByGidUid(GroupUserSign record) {
		// TODO Auto-generated method stub
		return this.groupUserSignDao.selectByGidUid(record);
	}

	@Override
	public void updateAllByNowmiles() {
		// TODO Auto-generated method stub
		this.groupUserSignDao.updateAllByNowmiles();
	}

}
