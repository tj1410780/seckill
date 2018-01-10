package com.mengdi.exception;
/**
 * 秒杀关闭异常
 * @author simon
 *
 */
public class SeckillCloseException extends SeckillException{

	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
