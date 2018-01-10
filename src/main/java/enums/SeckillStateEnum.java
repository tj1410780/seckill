package enums;
/**
 * 使用枚举表述常量数据字段
 * @author simon
 *
 */

public enum SeckillStateEnum {
	
	SUCCESS(1,"秒杀成功"),
	End(0,"秒杀结束"),
	REPEAT_KILL(-1,"重复秒杀"),
	Inner_ERROR(-2,"系统异常"),
	DATA_REWRITE(-3,"数据篡改");
	
	private int state;
	
	private String stateInfo;

	private SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	public static SeckillStateEnum stateOf(int index) {
		for (SeckillStateEnum stateEnum : values()) {
			if (stateEnum.getState() == index) {
				return stateEnum;
			}
		}
		return null;
	}
	
	
}
