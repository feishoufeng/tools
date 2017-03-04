package com.dopakoala.douba.quartz.jobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dopakoala.douba.entity.Hongbao;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.entity.UserMoneyLog;
import com.dopakoala.douba.service.IHongbaoService;
import com.dopakoala.douba.service.IUserMoneyLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.task.ImmediatelyTask;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

/**
 * @Description: 任务执行类
 */
public class ResumeJob implements Job {
	// 记录日志
	protected static final Logger LOG = LoggerFactory.getLogger(ImmediatelyTask.class);

	@Override
	public void execute(JobExecutionContext context) {
		try {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			String flag = jobDataMap.getString("flag");
			switch (flag) {
			case ConstantsUtils.QUARTZ_RETURN_REDPACKET_FLAG:
				redPacket(jobDataMap);
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

	public void redPacket(JobDataMap jobDataMap) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		IHongbaoService hongbaoService = (IHongbaoService)wac.getBean("hongbaoService");
		IUserMoneyLogService userMoneyLogService = (IUserMoneyLogService)wac.getBean("userMoneyLogService");
		IUserService userService = (IUserService)wac.getBean("userService");
		
		System.out.println(ConvertUtils.formatDateToString(new Date(), 3) + "=============红包自动返现开始");
		int id = jobDataMap.getInt("id");
		// 获取红包信息
		Hongbao hongbao = hongbaoService.selectByPrimaryKey(id);
		if (hongbao != null) {
			int status = hongbao.getStatus();
			int uid = hongbao.getUid();
			// 获取用户账户余额
			User user = userService.selectByPrimaryKey(uid);
			if (user != null) {
				// 当红包状态为正常状态才进行返还操作
				if (status == 1) {
					// 修改红包状态为过期状态
					Hongbao updateHongbao = new Hongbao();
					updateHongbao.setStatus(-1);
					updateHongbao.setReturnStatus(-1);
					updateHongbao.setId(id);
					int n = hongbaoService.updateByPrimaryKeySelective(updateHongbao);
					if (n == 1) {
						// 增加账户流水
						UserMoneyLog insertUserMoneyLog = new UserMoneyLog();
						insertUserMoneyLog.setAction("红包余额退还");
						insertUserMoneyLog.setType(1);
						insertUserMoneyLog.setMoney(hongbao.getLeftmoney());
						insertUserMoneyLog.setLeftmoney(user.getMoney() + hongbao.getLeftmoney());
						insertUserMoneyLog.setUid(uid);
						int m = userMoneyLogService.insertSelective(insertUserMoneyLog);
						if (m == 1) {
							// 改变红包退还状态
							Hongbao updateHongbao1 = new Hongbao();
							updateHongbao1.setReturnStatus(1);
							updateHongbao1.setId(id);
							int q = hongbaoService.updateByPrimaryKeySelective(updateHongbao1);
							if (q == 1) {
								// 账户增加
								User updateUser = new User();
								updateUser.setUid(uid);
								updateUser.setMoney(user.getMoney() + hongbao.getLeftmoney());
								int p = userService.updateByPrimaryKeySelective(updateUser);
								if (p == 1) {
									LOG.info("id为：" + id + "的红包的返现到账户成功");
								} else {
									LOG.info("id为：" + id + "的红包的返现到账户失败");
								}
							} else {
								LOG.info("id为：" + id + "的红包的返还状态更改失败");
							}
						} else {
							LOG.info("id为：" + id + "的红包登记账户流水失败");
						}
					} else {
						LOG.info("未获取到id为：" + id + "的红包的发送者信息");
					}
				}
			} else {
				LOG.info("未获取到id为：" + id + "的红包");
			}
		} else {
			LOG.info("未获取到id为：" + id + "的红包");
		}
		System.out.println(ConvertUtils.formatDateToString(new Date(), 3) + "=============红包自动返现结束");
	}
}
