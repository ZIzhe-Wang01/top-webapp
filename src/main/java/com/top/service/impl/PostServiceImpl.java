package com.top.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.top.dao.entity.Post;
import com.top.dao.mapper.PostMapper;
import com.top.dao.mapper.UserMapper;
import com.top.service.PostService;
import com.top.utils.PageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
	
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public PageResult<Post> findPage(Integer currentPage, Integer rows) {
		Page<Post> result = postMapper.selectPage(new Page<>(currentPage, rows), null);
		PageResult<Post> page = new PageResult<>();
		result.getRecords().forEach(post -> {
			post.setTime(post.getTime().substring(0, post.getTime().length() - 2));
			post.setUser(userMapper.selectById(post.getUserId()));
		});
		page.setList(result.getRecords());
		page.setTotal(result.getTotal());
		page.setRows(rows);
		page.setCurrentPage(currentPage);
		page.setTotalPage((int) Math.ceil(result.getTotal() * 1. / rows));
		return page;
	}
	
	@Override
	public void addLikeNum(String id) {
		postMapper.addLikeNum(id);
	}
}
