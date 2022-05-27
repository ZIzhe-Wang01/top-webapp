package com.top.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.top.dao.entity.Post;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
	
	void addLikeNum(String id);
}
