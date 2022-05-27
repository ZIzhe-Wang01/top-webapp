package com.top.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.top.dao.entity.Comment;
import com.top.utils.PageResult;


public interface CommentService extends IService<Comment> {
	
	PageResult<Comment> findPage(Integer currentPage, Integer rows);
}
