package com.mengdi.service;

import java.util.List;

import com.mengdi.dto.Register;
import com.mengdi.entity.User;

public interface UserService {	
	
	List<User> getUserList();
	
	User getUser(String userName, String password);
	
	Register userRegister(String userName, String password, long userPhone);
	
}
