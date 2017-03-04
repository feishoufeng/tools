package com.dopakoala.douba.service;

import com.dopakoala.douba.entity.PayLog;

public interface IPayLogService {
	public int deleteByPrimaryKey(int payid);

	public int insertSelective(PayLog record);

	public PayLog selectByPrimaryKey(int payid);

	public int updateByPrimaryKeySelective(PayLog record);
}