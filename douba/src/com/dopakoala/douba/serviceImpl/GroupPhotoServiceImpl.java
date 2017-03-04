package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupPhotoDao;
import com.dopakoala.douba.entity.GroupPhoto;
import com.dopakoala.douba.service.IGroupPhotoService;

@Service("groupPhotoService")
public class GroupPhotoServiceImpl implements IGroupPhotoService {

	@Resource
	private IGroupPhotoDao groupPhotoDao;
	
	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupPhotoDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupPhoto record) {
		// TODO Auto-generated method stub
		this.groupPhotoDao.insertSelective(record);
	}

	@Override
	public List<GroupPhoto> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupPhotoDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupPhoto record) {
		// TODO Auto-generated method stub
		this.groupPhotoDao.updateByPrimaryKeySelective(record);
	}

}
