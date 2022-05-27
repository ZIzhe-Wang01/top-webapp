package com.top.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.top.dao.entity.Follow;
import com.top.dao.entity.Post;
import com.top.dao.entity.User;
import com.top.service.FollowService;
import com.top.service.PostService;
import com.top.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;
	
	@Autowired
	private PostService postService;
	
	@RequestMapping("toLogin")
	public String toLogin(HttpServletRequest request) {
		request.getSession().removeAttribute("user");
		return "login";
	}
	
	@RequestMapping("toRegister")
	public String toRegister() {
		return "register";
	}
	
	@RequestMapping("login")
	public String login(User user, Model model, HttpServletRequest request) {
		User one = userService.getOne(new QueryWrapper<User>().eq("email", user.getEmail()).eq("password", user.getPassword()));
		if (!ObjectUtils.isEmpty(one)) {
			one.setList(followService.list(new QueryWrapper<Follow>().eq("user_id", one.getId())));
			request.getSession().setAttribute("user", one);
			return "redirect:/user/detail/" + one.getId();
		}
		model.addAttribute("error", "Incorrect username or password");
		return "login";
	}
	
	@RequestMapping("register")
	public String register(User user,Model model){
		User one = userService.getOne(new QueryWrapper<User>().eq("email", user.getEmail()));
		if (ObjectUtils.isEmpty(one)) {
			userService.save(user);
			followService.addFollow(user.getId(), 1);
			followService.addFollow(user.getId(), 2);
			return "redirect:/user/toLogin";
		}
		model.addAttribute("error","Email has been registered");
		return "register";
	}	
	
	@RequestMapping("detail/{id}")
	public String detailUser(@PathVariable Integer id, Model model,HttpServletRequest request) {
		Object u = request.getSession().getAttribute("user");
		if (ObjectUtils.isEmpty(u)) {
			return "redirect:/user/toLogin";
		}
		User user = userService.getById(id);
		List<Follow> followList = followService.list(new QueryWrapper<Follow>().eq("user_id", id));
		followList.forEach(follow -> {
			follow.setFollow(userService.getById(follow.getFollowId()));
		});
		List<Post> postList = postService.list(new QueryWrapper<Post>().eq("user_id", id));
		postList.forEach(post -> {
			post.setTime(post.getTime().substring(0, post.getTime().length() - 2));
		});
		model.addAttribute("user", user);
		model.addAttribute("postList", postList);
		model.addAttribute("followList", followList);
		model.addAttribute("flag", true);
		return "userCenter";
	}
	
	@RequestMapping("updUser")
	public String updUser(User user, HttpServletRequest request) {
		userService.updateById(user);
		User u = userService.getById(user.getId());
		request.getSession().setAttribute("user", u);
		return "redirect:/user/detail/" + user.getId();
	}
	
	@RequestMapping("updPassword")
	public String updPassword(Model model, User user, HttpServletRequest request) {
		User user1 = (User) request.getSession().getAttribute("user");
		if (user1.getPassword().equals(user.getPassword())) {
			user.setPassword(user.getNewPassword());
			userService.updateById(user);
			return "redirect:/user/toLogin";
		}
		request.getSession().removeAttribute("user");
		model.addAttribute("error", "Failed to change password");
		return "login";
	}
	
	@RequestMapping("upload")
	public String upload(MultipartFile img, Integer id, HttpServletRequest request) throws IOException {
		String propertyPath = System.getProperty("user.dir");
		String filename = UUID.randomUUID().toString();
		InputStream in = img.getInputStream();
		filename += ".jpg";
		userService.updImg(id, filename);
		FileOutputStream fos = new FileOutputStream(propertyPath + "\\target\\classes\\templates\\res\\images\\avatar\\" + filename);
		byte bf[] = new byte[1024];
		int l = 0;
		while ((l = in.read(bf)) != -1) {
			fos.write(bf, 0, l);
		}
		in.close();
		fos.close();
		img.transferTo(new File(propertyPath + "\\src\\main\\resources\\templates\\res\\images\\avatar\\" + filename));
		User user = userService.getById(id);
		request.getSession().setAttribute("user", user);
		return "redirect:/user/detail/" + id;
	}
	
	
}
