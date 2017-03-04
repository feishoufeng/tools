package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupUserDao;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.service.IGroupUserService;

@Service("groupUserService")
public class GroupUserServiceImpl implements IGroupUserService {

	@Resource
	private IGroupUserDao groupUserDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupUserDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupUser record) {
		// TODO Auto-generated method stub
		this.groupUserDao.insertSelective(record);
	}

	@Override
	public GroupUser selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupUser record) {
		// TODO Auto-generated method stub
		this.groupUserDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public GroupUser selectByUidGid(GroupUser record) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectByUidGid(record);
	}

	@Override
	public List<GroupUser> selectByGid(GroupUser record) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectByGid(record);
	}

	@Override
	public void updateByGidSelective(GroupUser record) {
		// TODO Auto-generated method stub
		this.groupUserDao.updateByGidSelective(record);

	}

	@Override
	public List<GroupUser> selectAdminByGid(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectAdminByGid(gid);
	}

	@Override
	public List<GroupUser> selectTopUserInfo(GroupUser record) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectTopUserInfo(record);
	}

	@Override
	public List<GroupUser> selectAdminNumByGid(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectAdminNumByGid(gid);
	}

	@Override
	public GroupUser selectOpenByGid(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectOpenByGid(gid);
	}

	@Override
	public List<GroupUser> selectNumByUid(Integer uid) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectNumByUid(uid);
	}

	@Override
	public List<GroupUser> selectAllOpen() {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectAllOpen();
	}

	@Override
	public GroupUser selectSuperByGid(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupUserDao.selectSuperByGid(gid);
	}
}
