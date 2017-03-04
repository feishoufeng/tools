package com.dopakoala.douba.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IGroupUserSignLogDao;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.service.IGroupUserSignLogService;

@Service("groupUserSignLogService")
public class GroupUserSignLogServiceImpl implements IGroupUserSignLogService {

	@Resource
	private IGroupUserSignLogDao groupUserSignLogDao;

	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		this.groupUserSignLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		this.groupUserSignLogDao.insertSelective(record);
	}

	@Override
	public GroupUserSignLog selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		this.groupUserSignLogDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<GroupUserSignLog> selectListByGid(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectListByGid(record);
	}

	@Override
	public GroupUserSignLog selectLastedLogByGid(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectLastedLogByGid(gid);
	}

	@Override
	public GroupUserSignLog selectTotalByGid(Integer gid) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectTotalByGid(gid);
	}

	@Override
	public List<GroupUserSignLog> selectByUidCardId(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectByUidCardId(record);
	}

	@Override
	public List<GroupUserSignLog> selectListByUidGidSE(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectListByUidGidSE(record);
	}

	@Override
	public GroupUserSignLog selectLastedLogByUidGid(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectLastedLogByUidGid(record);
	}

	@Override
	public GroupUserSignLog selectTotalByUid(Integer uid) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectTotalByUid(uid);
	}

	@Override
	public List<GroupUserSignLog> selectByCardidSignid(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectByCardidSignid(record);
	}

	@Override
	public void updateByCardidSignid(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		this.groupUserSignLogDao.updateByCardidSignid(record);
	}

	@Override
	public List<GroupUserSignLog> selectListByCardId(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectListByCardId(record);
	}

	@Override
	public GroupUserSignLog selectLastedLogByUid(Integer uid) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectLastedLogByUid(uid);
	}

	@Override
	public GroupUserSignLog selectTotalByCardId(Integer cardId) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectTotalByCardId(cardId);
	}

	@Override
	public GroupUserSignLog selectByCardIdUid(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectByCardIdUid(record);
	}

	@Override
	public List<GroupUserSignLog> selectListByCardIdUid(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectListByCardIdUid(record);
	}

	@Override
	public void updateByCardidSignidRevokemiles(GroupUserSignLog record) {
		// TODO Auto-generated method stub
		this.groupUserSignLogDao.updateByCardidSignidRevokemiles(record);
	}

	@Override
	public GroupUserSignLog selectStatisticByUid(Integer uid) {
		// TODO Auto-generated method stub
		return this.groupUserSignLogDao.selectStatisticByUid(uid);
	}

}
