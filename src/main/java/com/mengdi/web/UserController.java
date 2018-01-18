package com.mengdi.web;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.mengdi.dto.Register;
import com.mengdi.entity.User;
import com.mengdi.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
			return "register";
	}
	
	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	public String userRegister(
			@RequestParam("username") String userName,
			@RequestParam("password") String password,
			@RequestParam("userphone") long userPhone,
			Model model) {
		Register register = userService.userRegister(userName, password, userPhone);
		if (register.getState() == 1) {
			return "redirect:/user/login";
		} else {
			//model.addAttribute("message", "用户名或手机号码已注册");
			model.addAttribute("register",register.getStateInfo());
			return "register";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";	
	}
	
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public String userLogin(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("username") String userName,
			@RequestParam("password") String password,			
			Model model) {
		User user = userService.getUser(userName, password);
		if (user == null) {
			model.addAttribute("message", "用户名或密码错误，请重新登录");
			return "login";
		} else {
			System.out.println(user.getUserName());
			Cookie cookies[] = request.getCookies();    //将电话号码放到cookie中		
			if (cookies == null) {    //浏览器第一次访问该网页，cookie为空
				Cookie cookie = new Cookie("killPhone", String.valueOf(user.getUserPhone()));
				cookie.setMaxAge(60*60*24);
				response.addCookie(cookie);
			} else {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("killPhone")) {
						cookie.setValue(String.valueOf(user.getUserPhone()));				
						cookie.setMaxAge(60*60*24); //一天过期
						cookie.setPath("/seckill");
					    response.addCookie(cookie);
						break;
					} else {
						cookie = new Cookie("killPhone", String.valueOf(user.getUserPhone()));
						cookie.setMaxAge(60*60*24);
						response.addCookie(cookie);
					}
				}			
			}			
			return "redirect:/seckill/list";			
		}
		
	}
}
