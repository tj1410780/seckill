package com.mengdi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mengdi.entity.User;

public interface UserDao {
	
	/**
	 * 根据用户名、密码查询用户信息
	 * @param userName
	 * @return
	 */
	User queryByNameAndPassword(@Param("userName") String userName, @Param("password") String password);
	
	/**
	 * 根据偏移量查询用户信息
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<User> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	
	/**
	 * 用户注册时插入用户信息
	 * @param userName
	 * @param password
	 * @param userPhone
	 * @return
	 */
	int insertUser(@Param("userName") String userName, @Param("password") String password, @Param("userPhone") long userPhone);
	
	
}
