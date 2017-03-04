package com.dopakoala.douba.dao;

import java.util.List;

import com.dopakoala.douba.entity.User;

public interface IUserDao {
	int deleteByPrimaryKey(Integer uid);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer uid);
	
	User selectByOpenId(String openid);

	List<User> selectByGid(Integer gid);

	User selectByMobile(String mobile);

	User selectByLoginMsg(User record);

	List<User> selectAll(User record);

	int updateByPrimaryKeySelective(User record);
}