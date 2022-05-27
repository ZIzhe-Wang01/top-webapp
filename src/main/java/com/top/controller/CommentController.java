package com.top.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.top.dao.entity.Comment;
import com.top.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/comment/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("add")
	public String addComment(Integer userId, Integer postId, String text, MultipartFile img, HttpServletRequest request) throws IOException {
		Comment comment = new Comment();
		comment.setText(text);
		comment.setUserId(userId);
		comment.setPostId(postId);
		if (img.getOriginalFilename().equals("")) {
			commentService.save(comment);
		} else {
			String filename = UUID.randomUUID().toString();
			filename += img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
			comment.setImg(filename);
			String propertyPath = System.getProperty("user.dir");
			InputStream in = img.getInputStream();
			commentService.save(comment);
			FileOutputStream fos = new FileOutputStream(propertyPath + "\\target\\classes\\templates\\res\\images\\comment\\" + filename);
			byte bf[] = new byte[1024];
			int l = 0;
			while ((l = in.read(bf)) != -1) {
				fos.write(bf, 0, l);
			}
			in.close();
			fos.close();
			img.transferTo(new File(propertyPath + "\\src\\main\\resources\\templates\\res\\images\\comment\\" + filename));
		}
		return "redirect:/post/detail/" + comment.getPostId();
	}
	// public String add(MultipartFile filename, Integer forumId, String text, HttpServletRequest request) {
	// 	Comment comment = new Comment();
	// 	comment.setUserId(((User) request.getSession().getAttribute("user")).getId());
	// 	comment.setPostId(forumId);
	// 	comment.setText(text);
	// 	try {
	// 		if (!filename.getOriginalFilename().equals("")) {
	// 			String img = UUID.randomUUID().toString();
	// 			img += filename.getOriginalFilename().substring(filename.getOriginalFilename().lastIndexOf("."));
	// 			comment.setImg(img);
	// 			String propertyPath = System.getProperty("user.dir");
	// 			InputStream in = filename.getInputStream();
	// 			FileOutputStream fos = new FileOutputStream(propertyPath + "\\target\\classes\\templates\\res\\images\\" + img);
	// 			byte bf[] = new byte[1024];
	// 			int l = 0;
	// 			while ((l = in.read(bf)) != -1) {
	// 				fos.write(bf, 0, l);
	// 			}
	// 			in.close();
	// 			fos.close();
	// 			filename.transferTo(new File(propertyPath + "\\src\\main\\resources\\templates\\res\\images\\" + img));
	// 		}
	// 	}catch (Exception e){
	// 		e.printStackTrace();
	// 	}
	// 	commentService.save(comment);
	// 	return "redirect:/forum/detail/" + comment.getPostId();
	// }
}
