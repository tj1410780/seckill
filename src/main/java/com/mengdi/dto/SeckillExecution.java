package com.mengdi.dto;
/**
 * 封装秒杀执行后结果
 * @author simon
 *
 */

import com.mengdi.entity.SuccessKilled;
import com.mengdi.enums.SeckillStateEnum;

public class SeckillExecution {

	private long seckillId;
	//秒杀执行结果状态
	private int state;
	//状态表示
	private String stateInfo; //这里不好直接用enum枚举对象，因为之后要进行Json数据传递，枚举不方便
	//秒杀成功对象
	private SuccessKilled successKilled;
	
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.successKilled = successKilled;
	}
	
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
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

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}
	
	@Override
	public String toString() {
		return "SeckillExecution {" +
	"seckillId=" + seckillId + 
	", state=" + state + 
	", stateInfo='" + stateInfo + '\'' +
	", successKilled=" + successKilled +
	"}";	
	}
		
}
