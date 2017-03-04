package com.dopakoala.douba.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupPaceDao;
import com.dopakoala.douba.entity.GroupPace;
import com.dopakoala.douba.service.IGroupPaceService;

@Service("groupPaceService")
public class GroupPaceServiceImpl implements IGroupPaceService {

	@Autowired
	private IGroupPaceDao groupPaceDao;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupPaceDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(GroupPace record) {
		// TODO Auto-generated method stub
		return this.groupPaceDao.insertSelective(record);
	}

	@Override
	public GroupPace selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupPaceDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(GroupPace record) {
		// TODO Auto-generated method stub
		return this.groupPaceDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public GroupPace selectByGid(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupPaceDao.selectByGid(gid);
	}

}
