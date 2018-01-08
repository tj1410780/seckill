package com.mengdi.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mengdi.entity.Seckill;

/**
 * 配置spring和Junit整合，Junit启动时加载springIOC容器
 * spring-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件的位置
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	//注入dao接口实现类的依赖
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		long id = 1001L;
		Date killTime = new Date();
		int i = seckillDao.reduceNumber(id, killTime);
		System.out.println("updateCount = " + i);
	}

	@Test
	public void testQueryById() {
		long id = 1000L;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill);
	}
	
	//Seckill {seckillId=1000, name='1000元秒杀iPhone6', number=100, startTime=Fri Jan 05 00:00:00 CST 2018, endTime=Sat Jan 06 00:00:00 CST 2018, createTime=Mon Jan 08 12:32:38 CST 2018}


	@Test
	public void testQueryAll() {
		List<Seckill> list = seckillDao.queryAll(0, 4);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
//  Seckill {seckillId=1000, name='1000元秒杀iPhone6', number=100, startTime=Fri Jan 05 00:00:00 CST 2018, endTime=Sat Jan 06 00:00:00 CST 2018, createTime=Mon Jan 08 12:32:38 CST 2018}
//	Seckill {seckillId=1001, name='500元秒杀ipad2', number=200, startTime=Sun Jan 07 00:00:00 CST 2018, endTime=Tue Jan 09 00:00:00 CST 2018, createTime=Mon Jan 08 12:32:38 CST 2018}
//	Seckill {seckillId=1002, name='300元秒杀小米4', number=300, startTime=Wed Jan 10 00:00:00 CST 2018, endTime=Fri Jan 12 00:00:00 CST 2018, createTime=Mon Jan 08 12:32:38 CST 2018}
//	Seckill {seckillId=1003, name='600元秒杀红米note', number=500, startTime=Sat Jan 06 00:00:00 CST 2018, endTime=Mon Jan 08 00:00:00 CST 2018, createTime=Mon Jan 08 12:32:38 CST 2018}

}
