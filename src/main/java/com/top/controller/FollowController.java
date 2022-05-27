package com.top.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.top.dao.entity.Follow;
import com.top.dao.entity.User;
import com.top.service.FollowService;
import com.top.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user/")
public class FollowController {


    @Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;	

    @RequestMapping("addFollow/{userId}/{followId}")
	public String addFollow(@PathVariable Integer userId, @PathVariable Integer followId, HttpServletRequest request) {
		followService.addFollow(userId, followId);
		User one = ((User) request.getSession().getAttribute("user"));
		one.setList(followService.list(new QueryWrapper<Follow>().eq("user_id", userId)));
		return "redirect:/user/detail/" + followId;
	}
	
	@RequestMapping("delFollow/{userId}/{followId}")
	public String delFollow(@PathVariable Integer userId, @PathVariable Integer followId, HttpServletRequest request) {
		followService.remove(new QueryWrapper<Follow>().eq("user_id", userId).eq("follow_id", followId));
		User one = ((User) request.getSession().getAttribute("user"));
		one.setList(followService.list(new QueryWrapper<Follow>().eq("user_id", userId)));
		return "redirect:/user/detail/" + followId;
	}
	
	
	@RequestMapping("findListByNickName")
	public String findListByNickName(String nickname, Model model, HttpServletRequest request) {
		if(ObjectUtils.isEmpty(nickname)){
			Object nickname1 = request.getSession().getAttribute("nickname");
			nickname = nickname1.toString();
		}
		request.getSession().setAttribute("nickname", nickname);
		User user = (User) request.getSession().getAttribute("user");
		List<User> list = userService.list(new QueryWrapper<User>().like("nickname", nickname));
		for (User u : list) {
			for (Follow follow : user.getList()) {
				if (u.getId().equals(follow.getFollowId())) {
					u.setStatus("1");
					break;
				}
			}
		}
		model.addAttribute("userList", list);
		model.addAttribute("flag", true);
		return "searchResult";
	}
	
	@RequestMapping("addFollowByUserId/{userId}/{followId}")
	public String addFollowByUserId(@PathVariable Integer userId, @PathVariable Integer followId, HttpServletRequest request) {
		followService.addFollow(userId, followId);
		User one = ((User) request.getSession().getAttribute("user"));
		one.setList(followService.list(new QueryWrapper<Follow>().eq("user_id", userId)));
		return "redirect:/user/findListByNickName";
	}
	
	@RequestMapping("delFollowByUserId/{userId}/{followId}")
	public String delFollowByUserId(@PathVariable Integer userId, @PathVariable Integer followId, HttpServletRequest request) {
		followService.remove(new QueryWrapper<Follow>().eq("user_id", userId).eq("follow_id", followId));
		User one = ((User) request.getSession().getAttribute("user"));
		one.setList(followService.list(new QueryWrapper<Follow>().eq("user_id", userId)));
		return "redirect:/user/findListByNickName";
	}
}
