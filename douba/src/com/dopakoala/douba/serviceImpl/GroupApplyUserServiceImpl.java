package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupApplyUserDao;
import com.dopakoala.douba.entity.GroupApplyUser;
import com.dopakoala.douba.service.IGroupApplyUserService;

@Service("groupApplyUserService")
public class GroupApplyUserServiceImpl implements IGroupApplyUserService {

	@Resource
	private IGroupApplyUserDao groupApplyUserDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupApplyUserDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupApplyUser record) {
		// TODO Auto-generated method stub
		this.groupApplyUserDao.insertSelective(record);
	}

	@Override
	public GroupApplyUser selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupApplyUserDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupApplyUser record) {
		// TODO Auto-generated method stub
		this.groupApplyUserDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<GroupApplyUser> selectByGid(GroupApplyUser record) {
		// TODO Auto-generated method stub
		return this.groupApplyUserDao.selectByGid(record);
	}

	@Override
	public List<GroupApplyUser> selectByUid(Integer uid) {
		// TODO Auto-generated method stub
		return this.groupApplyUserDao.selectByUid(uid);
	}

	@Override
	public GroupApplyUser selectByGidUid(GroupApplyUser record) {
		// TODO Auto-generated method stub
		return this.groupApplyUserDao.selectByGidUid(record);
	}

	@Override
	public List<GroupApplyUser> selectCheckByGidUid(GroupApplyUser record) {
		// TODO Auto-generated method stub
		return this.groupApplyUserDao.selectCheckByGidUid(record);
	}

}
