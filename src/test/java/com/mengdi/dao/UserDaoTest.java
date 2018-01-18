package com.mengdi.dao;

import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mengdi.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class UserDaoTest {
	
	@Resource
	UserDao userDao;
	
	@Test
	public void testQueryByNameAndPassword() {
		String userName = "Alice";
		String password = "2384fj";
		User user = userDao.queryByNameAndPassword(userName, password);
		System.out.println(user);
	}
	//User {userId=2, userName=Alice, password=2384fj, userPhone=12876565789}

	@Test
	public void testQueryAll() {
		List<User> list = userDao.queryAll(0, 3);
		for (User user : list) {
			System.out.println(user);
		}
	}
	//User {userId=2, userName=Alice, password=2384fj, userPhone=12876565789}
	//User {userId=1, userName=Jack, password=123a, userPhone=12345678989}
	
	@Test
	public void testInsertUser() {
		String userName = "Alice";
		String password = "2384fj";
		long userPhone = 12876565789L;
		int insertCount = userDao.insertUser(userName, password, userPhone);
		System.out.println(insertCount);				
	}
}
