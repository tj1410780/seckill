package com.mengdi.service.Impl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.mengdi.dao.UserDao;
import com.mengdi.dto.Register;
import com.mengdi.entity.User;
import com.mengdi.enums.RegisterStateEnum;
import com.mengdi.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private UserDao userDao;

	public List<User> getUserList() {
		List<User> list = userDao.queryAll(0, 10);
		return list;
	}
	
	public User getUser(String userName, String password) {
		User user = userDao.queryByNameAndPassword(userName, password);
		return user;
	}

	public Register userRegister(String userName, String password, long userPhone) {
		try {
			int insertCount = userDao.insertUser(userName, password, userPhone);
			if (insertCount <= 0) {
				return new Register(false, RegisterStateEnum.Fail);
			} else {
				return new Register(true, RegisterStateEnum.SUCCESS);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return new Register(false, RegisterStateEnum.ERROR);
		}				
	}

	
}
