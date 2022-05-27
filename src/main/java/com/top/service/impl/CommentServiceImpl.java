package com.top.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.top.dao.entity.Comment;
import com.top.dao.mapper.CommentMapper;
import com.top.dao.mapper.UserMapper;
import com.top.service.CommentService;
import com.top.utils.PageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
	
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public PageResult<Comment> findPage(Integer currentPage, Integer rows) {
		
		Page<Comment> result = commentMapper.selectPage(new Page<>(currentPage, rows), null);
		PageResult<Comment> page = new PageResult<>();
		result.getRecords().forEach(comment -> {
			comment.setTime(comment.getTime().substring(0, comment.getTime().length() - 2));
			comment.setUser(userMapper.selectById(comment.getUserId()));
		});
		page.setList(result.getRecords());
		page.setTotal(result.getTotal());
		page.setRows(rows);
		page.setCurrentPage(currentPage);
		page.setTotalPage((int) Math.ceil(result.getTotal() * 1. / rows));
		return page;
	}
}
