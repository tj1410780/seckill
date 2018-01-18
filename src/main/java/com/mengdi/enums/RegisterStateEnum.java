package com.mengdi.enums;

public enum RegisterStateEnum {
	
	SUCCESS(1, "注册成功"),
	Fail(0, "注册失败"),
	ERROR(-1, "系统错误");
	
	private int state;
	
	private String stateInfo;
	
	private RegisterStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
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
