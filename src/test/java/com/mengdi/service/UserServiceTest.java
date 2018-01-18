package com.mengdi.service;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.mengdi.dto.Register;
import com.mengdi.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class UserServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private UserService userService;
	
	@Test
	public void testGetUser() {
		String userName = "Jack";
		String password ="123a";
		User user = userService.getUser(userName, password);
		logger.info("user = {}", user);
	}
	//user = User {userId=1, userName=Jack, password=123a, userPhone=12345678989}

	@Test
	public void testGetUserList() {
		List<User> list = userService.getUserList();
		logger.info("list = {}", list);
	}
	//list = [User {userId=2, userName=Alice, password=2384fj, userPhone=12876565789}, User {userId=1, userName=Jack, password=123a, userPhone=12345678989}]

	@Test
	public void testUserRegister() {
		String userName = "David";
		String password = "12345as";
		long userPhone = 18964212123L;
		Register register = userService.userRegister(userName, password, userPhone);
		logger.info("succeed = {}", register.isSucceed());
		logger.info("state = {}", register.getState());
		logger.info("stateInfo = {}", register.getStateInfo());
	}
	//succeed = true
	//state = 1
	//stateInfo = 注册成功
	//第二次执行
	//succeed = false
	//state = 0
	//stateInfo = 注册失败

}
