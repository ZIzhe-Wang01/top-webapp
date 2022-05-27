package com.top.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.top.dao.entity.Comment;
import com.top.dao.entity.Follow;
import com.top.dao.entity.Manage;
import com.top.dao.entity.Post;
import com.top.dao.entity.User;
import com.top.service.CommentService;
import com.top.service.FollowService;
import com.top.service.ManageService;
import com.top.service.PostService;
import com.top.service.UserService;
import com.top.utils.MessageUtils;
import com.top.utils.PageResult;
import com.top.utils.RespBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/")
public class ManageController {
    
    @Autowired
    private ManageService manageService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;
    
    @RequestMapping("toLogin")
	public String toLogin() {
		return "manage/login";
	}

    @RequestMapping("login")
	public String login(Model model, Manage manage, HttpServletRequest request) {
		Manage m = manageService.getOne(new QueryWrapper<Manage>().eq("username", manage.getUsername()));
		if (!ObjectUtils.isEmpty(m)) {
			String password = DigestUtils.md5DigestAsHex(manage.getPassword().getBytes(StandardCharsets.UTF_8));
			boolean flag = password.equals(m.getPassword());
			if (flag) {
				request.getSession().setAttribute("manage", m);
				return "redirect:/manage/findUserPage/1/10";
			}
		}
		model.addAttribute("error", "Incorrect username or password");
		return "manage/login";
	}


    @RequestMapping("findUserPage/{currentPage}/{rows}")
	public String findPage(Model model, @PathVariable Integer currentPage, @PathVariable Integer rows) {
		Page<User> result = userService.page(new Page<>(currentPage, rows));
		PageResult<User> page = new PageResult<>();
		page.setList(result.getRecords());
		page.setTotal(result.getTotal());
		page.setRows(rows);
		page.setCurrentPage(currentPage);
		page.setTotalPage((int) Math.ceil(result.getTotal() * 1. / rows));
		model.addAttribute("userPb", page);
		return "manage/user";
	}

    @RequestMapping("findUser/{id}")
	@ResponseBody
	public RespBean findUserById(@PathVariable Integer id) {
		User user = userService.getById(id);
		if (!ObjectUtils.isEmpty(user)) {
			return RespBean.success(MessageUtils.QUERY_SUCCESS, user);
		}
		return RespBean.error(MessageUtils.QUERY_FAIL);
	}

    @RequestMapping("delUser/{id}")
	public String delUser(@PathVariable Integer id) {
		followService.remove(new QueryWrapper<Follow>().eq("user_id", id));
		commentService.remove(new QueryWrapper<Comment>().eq("user_id", id));
		userService.removeById(id);
		return "redirect:/manage/findUserPage/1/10";
	}

    @RequestMapping("findPostPage/{currentPage}/{rows}")
	public String findPostPage(Model model, @PathVariable Integer currentPage, @PathVariable Integer rows) {
		PageResult<Post> page = postService.findPage(currentPage, rows);
		List<User> list = userService.list();
		model.addAttribute("postPb", page);
		model.addAttribute("userList", list);
		return "manage/post";
	}

    @RequestMapping("findPost/{id}")
	@ResponseBody
	public RespBean findPost(@PathVariable Integer id) {
		Post post = postService.getById(id);
		post.setTime(post.getTime().substring(0, post.getTime().length() - 2));
		
		return RespBean.success(MessageUtils.QUERY_SUCCESS, post);
	}
	
    @RequestMapping("delPost/{id}")
	public String delPost(@PathVariable Integer id) {
		commentService.remove(new QueryWrapper<Comment>().eq("post_id", id));
		postService.removeById(id);
		return "redirect:/manage/findPostPage/1/10";
	}


    @RequestMapping("findCommentPage/{currentPage}/{rows}")
	public String findCommentPage(Model model, @PathVariable Integer currentPage, @PathVariable Integer rows) {
		PageResult<Comment> page = commentService.findPage(currentPage, rows);
		List<User> list = userService.list();
		model.addAttribute("commentPb", page);
		model.addAttribute("userList", list);
		return "manage/comment";
	}

    @RequestMapping("findComment/{id}")
	@ResponseBody
	public RespBean findCommentById(@PathVariable Integer id) {
		Comment comment = commentService.getById(id);
		comment.setTime(comment.getTime().substring(0, comment.getTime().length() - 2));
		return RespBean.success(MessageUtils.QUERY_SUCCESS, comment);
	}
    
    @RequestMapping("delComment/{id}")
	public String delComment(@PathVariable Integer id) {
		commentService.removeById(id);
		return "redirect:/manage/findCommentPage/1/10";
	}
	
}
