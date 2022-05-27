package com.top.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.top.dao.entity.User;
import com.top.dao.mapper.UserMapper;
import com.top.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Override
	public void updImg(Integer id, String filename) {
		userMapper.updImg(id,filename);
	}
}
