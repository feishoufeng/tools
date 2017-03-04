package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupTopicDao;
import com.dopakoala.douba.entity.GroupTopic;
import com.dopakoala.douba.service.IGroupTopicService;

@Service("groupTopicService")
public class GroupTopicServiceImpl implements IGroupTopicService {

	@Resource
	private IGroupTopicDao groupTopicDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupTopicDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupTopic record) {
		// TODO Auto-generated method stub
		this.groupTopicDao.insertSelective(record);
	}

	@Override
	public List<GroupTopic> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupTopicDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupTopic record) {
		// TODO Auto-generated method stub
		this.groupTopicDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<GroupTopic> selectByLimit(Integer gid, Integer page, Integer pagesize, String type) {
		// TODO Auto-generated method stub
		return this.groupTopicDao.selectByLimit(gid, page, pagesize, type);
	}

}
