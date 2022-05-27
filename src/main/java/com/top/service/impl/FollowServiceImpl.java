package com.top.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.top.dao.entity.Follow;
import com.top.dao.mapper.FollowMapper;
import com.top.service.FollowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
	
	@Autowired
	private FollowMapper followMapper;
	
	@Override
	public void addFollow(Integer userId, Integer followId) {
		followMapper.addFollow(userId,followId);
	}
}
