package com.b127.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.b127.web.dto.LoginRequest;
import com.b127.web.dto.LoginResponse;
import com.b127.web.entity.User;
import com.b127.web.services.AuthService;

@Controller
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "LogIn";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerUser() {
		return "register";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("loginRequest") LoginRequest loginRequest, Model model, HttpSession httpSession ) {
		LoginResponse loginResponse = authService.loginUser(loginRequest);
		model.addAttribute("user", loginResponse);
		
		
		if(loginResponse.getIsAdmin()) {
			httpSession.setAttribute("name", loginResponse.getAdminDashboard().getUser().getFirstname());
			httpSession.setAttribute("id", loginResponse.getAdminDashboard().getUser().getId());
			return "admin";
		}else {
			httpSession.setAttribute("name", loginResponse.getUserDashboard().getUser().getFirstname());
			httpSession.setAttribute("id", loginResponse.getUserDashboard().getUser().getId());
			return "client";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User user, Model model, HttpSession httpSession ) {
		LoginResponse loginResponse = authService.registerUser(user);
		model.addAttribute("user", loginResponse);
		
		
		if(loginResponse.getIsAdmin()) {
			httpSession.setAttribute("name", loginResponse.getAdminDashboard().getUser().getFirstname());
			httpSession.setAttribute("id", loginResponse.getAdminDashboard().getUser().getId());
			return "admin";
		}else {
			httpSession.setAttribute("name", loginResponse.getUserDashboard().getUser().getFirstname());
			httpSession.setAttribute("id", loginResponse.getUserDashboard().getUser().getId());
			return "client";
		}
	}
}
