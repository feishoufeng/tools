package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupAppDao;
import com.dopakoala.douba.entity.GroupApp;
import com.dopakoala.douba.service.IGroupAppService;

@Service("groupAppService")
public class GroupAppServiceImpl implements IGroupAppService {

	@Resource
	private IGroupAppDao groupAppDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupAppDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupApp record) {
		// TODO Auto-generated method stub
		this.groupAppDao.insertSelective(record);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupApp record) {
		// TODO Auto-generated method stub
		this.groupAppDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<GroupApp> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupAppDao.selectByPrimaryKey(id);
	}

}
