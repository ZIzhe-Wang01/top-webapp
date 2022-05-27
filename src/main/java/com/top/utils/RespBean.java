package com.top.utils;

public class RespBean {
	
	private long code;
	private String message;
	private Object obj;
	
	public RespBean(long code, String message, Object obj) {
		code = this.code;
		message = this.message;
		obj = this.obj;
	}

	/**
	 * success
	 */
	public static RespBean success(String message) {
		return new RespBean(200, message, null);
	}
	
	/**
	 * success
	 */
	public static RespBean success(String message, Object obj) {
		return new RespBean(200, message, obj);
	}
	
	/**
	 * fail
	 */
	public static RespBean error(String message) {
		return new RespBean(500, message, null);
	}
	
	/**
	 * fail
	 */
	public static RespBean error(String message, Object obj) {
		return new RespBean(500, message, obj);
	}
}
