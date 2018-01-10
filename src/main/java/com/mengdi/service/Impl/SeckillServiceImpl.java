package com.mengdi.service.Impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import com.mengdi.dao.SeckillDao;
import com.mengdi.dao.SuccessKilledDao;
import com.mengdi.dto.Exposer;
import com.mengdi.dto.SeckillExecution;
import com.mengdi.entity.Seckill;
import com.mengdi.entity.SuccessKilled;
import com.mengdi.enums.SeckillStateEnum;
import com.mengdi.exception.RepeatKillException;
import com.mengdi.exception.SeckillCloseException;
import com.mengdi.exception.SeckillException;
import com.mengdi.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private SeckillDao seckillDao;
	@Resource
	private SuccessKilledDao successKilledDao;
	
	//md5盐值字符串，用于混淆md5
	private final String slat = "dakfjerireopqwe4857x67f^%IOhdus";
	
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if (seckill == null) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//系统当前时间
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		//转化特定字符串的过程，不可逆
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}
	
	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes()); //spring提供的一个工具类
		return md5;
	}
	 /** 
     * 使用注解控制事务方法的优点: 
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格 
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部 
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制 
     */  
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑： 减库存 + 记录购买行为
		Date killTime = new Date();
		try {
			//减库存
			 int updateCount = seckillDao.reduceNumber(seckillId, killTime);
			 if (updateCount <= 0) {
				 //没有更新到记录，秒杀结束
				 throw new SeckillCloseException("seckill is closed");
			 } else {
				 //记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				//联合主键：seckillId, userPhone
				if (insertCount <= 0) {
					//重复秒杀
					throw new RepeatKillException("seckill repeat");
				} else {
					//秒杀成功
					SuccessKilled successKilled = successKilledDao.queryWithSeckill(seckillId, userPhone);
					successKilled.setState((short) 1);
					return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//所有编译期异常转化为运行期异常
			throw new SeckillException("seckill inner error" + e.getMessage());
		}
		
	}

}
