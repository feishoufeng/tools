package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupLevelDao;
import com.dopakoala.douba.entity.GroupLevel;
import com.dopakoala.douba.service.IGroupLevelService;

@Service("groupLevelService")
public class GroupLevelServiceImpl implements IGroupLevelService {

	@Resource
	private IGroupLevelDao groupLevelDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupLevelDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupLevel record) {
		// TODO Auto-generated method stub
		this.groupLevelDao.insertSelective(record);
	}

	@Override
	public List<GroupLevel> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupLevelDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupLevel record) {
		// TODO Auto-generated method stub
		this.groupLevelDao.updateByPrimaryKeySelective(record);
	}

}
