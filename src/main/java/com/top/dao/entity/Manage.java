package com.top.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("manage")

public class Manage implements Serializable {
    private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
