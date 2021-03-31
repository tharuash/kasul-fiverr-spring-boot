package com.b127.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.b127.web.dto.TicketReply;
import com.b127.web.entity.User;
import com.b127.web.services.AuthService;
import com.b127.web.services.TicketService;
import com.b127.web.services.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private AuthService authService;
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String adminProfile(Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		model.addAttribute("user", userService.getUser(userId) );
		return "myProfileAdmin";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String adminProfileUpdate(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		user.setId(userId);
		userService.updateAdminProfie(user);
		return "redirect:/admin/profile";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUsersByAdmin(Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		model.addAttribute("users", userService.getAllUsersByAdmin(userId) );
		return "Users";
	}
	
	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public String deleteUsersByAdmin(@PathVariable("id") Long userId ) {
		userService.deleteUser(userId);
		return "redirect:/admin/users";
	}
	
	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public String adminInbox(Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		model.addAttribute("messages", ticketService.getAdminTickets(userId) );
		return "Inbox_admin";
	}
	
	@GetMapping(value = "/ticket/{id}")
	public String adminViewMessage(@PathVariable("id") long messageId, Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		model.addAttribute("ticket", ticketService.getTicketByAdmin(messageId, userId));
		return "readMessageAdmin";
	}
	
	@RequestMapping(value = "/support", method = RequestMethod.GET)
	public String adminSupport(Model model, HttpSession httpSession) {
		return "SpportAdmin";
	}
	
	@RequestMapping(value = "/support", method = RequestMethod.POST)
	public String adminSupportAdd(Model model, HttpSession httpSession, @ModelAttribute("ticketReply") TicketReply ticketReply ) {
		long userId = (long) httpSession.getAttribute("id");
		ticketService.UpdateTicketReply(ticketReply, userId);
		return "redirect:/admin/inbox";
	}
	
	@GetMapping(value = "/add")
	public String adminAdd(Model model, HttpSession session) {
		long userId = (long)session.getAttribute("id");
		model.addAttribute("admins", userService.getAdminUsers(userId));
		return "supportAddAdmin";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String adminAddUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
		authService.registerAdmin(user);
		return "redirect:/admin/add";
	}
	
	@RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
	public String deleteAdminByAdmin(@PathVariable("id") Long userId ) {
		userService.deleteUser(userId);
		return "redirect:/admin/add";
	}

}
