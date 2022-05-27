package com.top.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

import java.io.Serializable;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
@TableName("follow")
public class Follow implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@TableId("user_id")
	private Integer userId;
	
	@TableField("follow_id")
	private Integer followId;
	
	@TableField(exist = false)
	private User follow;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFollowId() {
		return followId;
	}

	public void setFollowId(Integer followId) {
		this.followId = followId;
	}

	public User getFollow() {
		return follow;
	}

	public void setFollow(User follow) {
		this.follow = follow;
	}
	
	
}
