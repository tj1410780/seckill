package com.mengdi.service;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.mengdi.dto.Exposer;
import com.mengdi.dto.SeckillExecution;
import com.mengdi.entity.Seckill;
import com.mengdi.exception.RepeatKillException;
import com.mengdi.exception.SeckillCloseException;
import com.mengdi.exception.SeckillException;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills = seckillService.getSeckillList();
		logger.info("list={}", seckills);
	}

	@Test
	public void testGetById() {
		long seckillId = 1000L;
		Seckill seckill = seckillService.getById(seckillId);
		logger.info("seckill={}", seckill);
	}

	@Test
	public void testSeckillLogic() {
		long seckillId = 1000L;		
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) { //如果暴露则可执行秒杀操作
			logger.info("exposer={}", exposer);
			long userPhone = 18964212950L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
				logger.info("SeckillExecution={}", seckillExecution);
			} catch (RepeatKillException e) {  //一定要处理异常，否则程序会中止运行
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			} catch (SeckillException e) {
				logger.error(e.getMessage());
			}
		} else {  //否则，显示系统时间
			logger.warn("exposer={}", exposer);
		}
	}
	//显示系统时间
	//exposer=Exposer {seckillId=1002, exposed=false, md5='null', 
	//startTime=1515513600000, endTime=1515686400000, nowTime=1515507033645}
	
	//暴露秒杀地址
	//exposer=Exposer {seckillId=1000, exposed=true, md5='3f5d056e7b68b03b9756a8966fb19f26', 
	//startTime=0, endTime=0, nowTime=0}
	
	//秒杀商品不存在
	//exposer=Exposer {seckillId=1006, exposed=false, md5='null', 
	//startTime=0, endTime=0, nowTime=0}

	//执行秒杀成功返回的结果
	//SeckillExecution=SeckillExecution {seckillId=1000, state=1, stateInfo='秒杀成功', successKilled=SuccessKilled{seckillId=1000 ,userPhone=18964212936 ,
	//state=1 ,createTime=Tue Jan 09 22:17:27 CST 2018}}

}
