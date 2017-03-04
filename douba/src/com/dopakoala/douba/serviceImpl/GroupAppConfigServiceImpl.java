package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupAppConfigDao;
import com.dopakoala.douba.entity.GroupAppConfig;
import com.dopakoala.douba.service.IGroupAppConfigService;

@Service("groupAppConfigService")
public class GroupAppConfigServiceImpl implements IGroupAppConfigService {

	@Resource
	private IGroupAppConfigDao groupAppConfigDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupAppConfigDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupAppConfig record) {
		// TODO Auto-generated method stub
		this.groupAppConfigDao.insertSelective(record);
	}

	@Override
	public List<GroupAppConfig> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupAppConfigDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupAppConfig record) {
		// TODO Auto-generated method stub
		this.groupAppConfigDao.updateByPrimaryKeySelective(record);
	}
}
