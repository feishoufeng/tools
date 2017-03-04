package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.SimpleUser;

public interface ISimpleUserService {
	public SimpleUser selectUser(Integer uid, Integer gid);
	
	public SimpleUser selectUserByUid(Integer uid);
}