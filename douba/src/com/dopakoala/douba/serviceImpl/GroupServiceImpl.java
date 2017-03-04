package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupDao;
import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.service.IGroupService;

@Service("groupService")
public class GroupServiceImpl implements IGroupService {

	@Resource
	private IGroupDao groupDao;

	@Override
	public void deleteByPrimaryKey(Integer gid) {
		// TODO Auto-generated method stub
		this.groupDao.deleteByPrimaryKey(gid);
	}

	@Override
	public void insertSelective(Group record) {
		// TODO Auto-generated method stub
		this.groupDao.insertSelective(record);
	}

	@Override
	public Group selectByUidGid(Group record) {
		// TODO Auto-generated method stub
		return this.groupDao.selectByUidGid(record);
	}

	@Override
	public void updateByPrimaryKeySelective(Group record) {
		// TODO Auto-generated method stub
		this.groupDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Group> selectTypeNameByPid(Group record) {
		// TODO Auto-generated method stub
		return this.groupDao.selectTypeNameByPid(record);
	}

	@Override
	public List<Group> selectTypeNameByKeyword(Group record) {
		// TODO Auto-generated method stub
		return this.groupDao.selectTypeNameByKeyword(record);
	}

	@Override
	public Group selectMainByPid(Integer pid) {
		// TODO Auto-generated method stub
		return this.groupDao.selectMainByPid(pid);
	}

	@Override
	public Group selectTempByPid(Integer pid) {
		// TODO Auto-generated method stub
		return this.groupDao.selectTempByPid(pid);
	}

	@Override
	public Group selectByPrimaryKey(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupDao.selectByPrimaryKey(gid);
	}

	@Override
	public List<Group> selectGroupByUid(Group record) {
		// TODO Auto-generated method stub
		return this.groupDao.selectGroupByUid(record);
	}

	@Override
	public List<Group> selectPaotuanByUid(Integer uid) {
		// TODO Auto-generated method stub
		return this.groupDao.selectPaotuanByUid(uid);
	}

	@Override
	public void updateQuitByPid(Integer pid) {
		// TODO Auto-generated method stub
		this.groupDao.updateQuitByPid(pid);
	}

	@Override
	public List<Group> selectByPidUid(Group record) {
		// TODO Auto-generated method stub
		return this.groupDao.selectByPidUid(record);
	}

	@Override
	public List<Group> selectPaoTuan(Group record) {
		// TODO Auto-generated method stub
		return this.groupDao.selectPaoTuan(record);
	}

	@Override
	public List<Group> selectGroup(Group record) {
		// TODO Auto-generated method stub
		return this.groupDao.selectGroup(record);
	}

}
