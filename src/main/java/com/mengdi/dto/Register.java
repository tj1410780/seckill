package com.mengdi.dto;

import com.mengdi.enums.RegisterStateEnum;

/**
 * 返回注册后的结果（成功或者失败）
 * @author simon
 *
 */
public class Register {
	
	private boolean succeed;
	
	private int state;
	
	private String stateInfo;	

	public Register(boolean succeed, RegisterStateEnum stateEnum) {
		super();
		this.succeed = succeed;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}	

	public boolean isSucceed() {
		return succeed;
	}

	public void setSuccess(boolean succeed) {
		this.succeed = succeed;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
		
}
