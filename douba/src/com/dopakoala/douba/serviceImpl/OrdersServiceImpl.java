package com.dopakoala.douba.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dopakoala.douba.dao.IOrdersDao;
import com.dopakoala.douba.entity.Orders;
import com.dopakoala.douba.service.IOrdersService;

@Service("ordersService")
public class OrdersServiceImpl implements IOrdersService {

	@Autowired
	private IOrdersDao orderDao;
	
	@Override
	public int deleteByPrimaryKey(Integer orderId) {
		// TODO Auto-generated method stub
		return this.orderDao.deleteByPrimaryKey(orderId);
	}

	@Override
	public int insertSelective(Orders record) {
		// TODO Auto-generated method stub
		return this.orderDao.insertSelective(record);
	}

	@Override
	public Orders selectByPrimaryKey(Integer orderId) {
		// TODO Auto-generated method stub
		return this.orderDao.selectByPrimaryKey(orderId);
	}

	@Override
	public int updateByPrimaryKeySelective(Orders record) {
		// TODO Auto-generated method stub
		return this.orderDao.updateByPrimaryKeySelective(record);
	}

}
