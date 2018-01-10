package com.mengdi.dao;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mengdi.entity.SuccessKilled;
/**
 * 配置spring和Junit整合，Junit启动时加载springIOC容器
 * spring-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件的位置
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() {
		long seckillId = 1003L;
		long userPhone = 18964212920L;		
		int i = successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println("insertCount = " + i );
	}
	//第一次结果：insertCount = 1
	//第二次结果：insertCount = 0 即不能重复秒杀
	

	@Test
	public void testQueryWithSeckill() {
		long seckillId = 1003L;
		long userPhone = 18964212920L;
		SuccessKilled successKilled = successKilledDao.queryWithSeckill(seckillId, userPhone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());				
	}
	//SuccessKilled {seckillId=1003, userPhone=18964212920, state=0, createTime=Mon Jan 08 16:51:58 CST 2018}
	//Seckill {seckillId=1003, name='600元秒杀红米note', number=500, startTime=Sat Jan 06 00:00:00 CST 2018, endTime=Mon Jan 08 00:00:00 CST 2018, createTime=Mon Jan 08 12:32:38 CST 2018}


}
