package com.top.controller;

import com.top.dao.entity.Post;
import com.top.service.PostService;
import com.top.utils.MessageUtils;
import com.top.utils.PageResult;
import com.top.utils.RespBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/forum/")
public class ForumController {
	
	@Autowired
	private PostService postService;

	
	@RequestMapping("findPost/{id}")
	@ResponseBody
	public RespBean findPost(@PathVariable Integer id) {
		Post post = postService.getById(id);
		post.setTime(post.getTime().substring(0, post.getTime().length() - 2));
		
		return RespBean.success(MessageUtils.QUERY_SUCCESS, post);
	}
	
	@RequestMapping("findList/{currentPage}/{rows}")
	public String findPageToIndex(Model model, @PathVariable Integer currentPage, @PathVariable Integer rows) {
		PageResult<Post> page = postService.findPage(currentPage, rows);
		model.addAttribute("postList", page);
		return "forum";
	}
		
}
