package com.dopakoala.douba.testmybatis;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);

	@Test
	public void test1() {
		logger.info(1);
	}
	
	public static void main(String[] args) {
		logger.info(1);
	}
}
