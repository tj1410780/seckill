package com.mengdi.entity;

public class User {
	
	private long userId;
	
	private String userName;
	
	private String password;
	
	private long userPhone;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}
	
	@Override
	public String toString() {
		return "User {" +
				"userId=" + userId + 
				", userName=" + userName + 
				", password=" + password + 
				", userPhone=" + userPhone +
				"}";	
	}
}
