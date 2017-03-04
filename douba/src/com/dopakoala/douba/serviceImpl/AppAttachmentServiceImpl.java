package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IAppAttachmentDao;
import com.dopakoala.douba.entity.AppAttachment;
import com.dopakoala.douba.service.IAppAttachmentService;

@Service("AppAttachmentService")
public class AppAttachmentServiceImpl implements IAppAttachmentService{
	
	@Resource
	private IAppAttachmentDao appAttachmentDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.appAttachmentDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(AppAttachment record) {
		// TODO Auto-generated method stub
		this.appAttachmentDao.insertSelective(record);
	}

	@Override
	public List<AppAttachment> selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.appAttachmentDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(AppAttachment record) {
		// TODO Auto-generated method stub
		this.appAttachmentDao.updateByPrimaryKeySelective(record);
	}
}
