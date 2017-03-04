package com.dopakoala.douba.dao;

import org.apache.ibatis.annotations.Param;

import com.dopakoala.douba.entity.SimpleUser;

public interface ISimpleUserDao {
	SimpleUser selectUser(@Param("uid") Integer uid, @Param("gid") Integer gid);

	SimpleUser selectUserByUid(Integer uid);
}