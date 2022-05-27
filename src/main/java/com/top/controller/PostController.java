package com.top.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.top.dao.entity.Comment;
import com.top.dao.entity.Post;
import com.top.dao.entity.User;
import com.top.service.CommentService;
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
@RequestMapping("/post/")
public class PostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("detail/{id}")
	public String detail(@PathVariable Integer id, Model model, HttpServletRequest request) {
		Object user = request.getSession().getAttribute("user");
		if (ObjectUtils.isEmpty(user)) {
			return "redirect:/user/toLogin";
		}
		Post post = postService.getById(id);
		post.setTime(post.getTime().substring(0, post.getTime().length() - 2));
		post.setUser(userService.getById(post.getUserId()));
		model.addAttribute("post", post);
		List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("post_id", id));
		comments.forEach(comment -> {
			comment.setTime(comment.getTime().substring(0, comment.getTime().length() - 2));
			comment.setUser(userService.getById(comment.getUserId()));
		});
		model.addAttribute("comments", comments);
		return "detail";
	}
	
	
	@RequestMapping("toAddPost")
	public String toAddPost() {
		return "post";
	}
	
	@RequestMapping("addPost")
	public String addPost(MultipartFile img, String text, String title, HttpServletRequest request) throws IOException {
		User user = (User) request.getSession().getAttribute("user");
		Post post = new Post();
		post.setText(text);
		post.setTitle(title);
		post.setUserId(user.getId());
		if (img.getOriginalFilename().equals("")) {
			postService.save(post);
		} else {
			String filename = UUID.randomUUID().toString();
			filename += img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
			post.setImg(filename);
			String propertyPath = System.getProperty("user.dir");
			InputStream in = img.getInputStream();
			postService.save(post);
			FileOutputStream fos = new FileOutputStream(propertyPath + "\\target\\classes\\templates\\res\\images\\post\\" + filename);
			byte bf[] = new byte[1024];
			int l = 0;
			while ((l = in.read(bf)) != -1) {
				fos.write(bf, 0, l);
			}
			in.close();
			fos.close();
			img.transferTo(new File(propertyPath + "\\src\\main\\resources\\templates\\res\\images\\post\\" + filename));
		}
		return "redirect:/post/detail/" + post.getId();
	}

	@RequestMapping("addLikeNum/{id}")
	public String addLikeNum(@PathVariable String id) {
		postService.addLikeNum(id);
		return "redirect:/post/detail/" + id;
	}
}
