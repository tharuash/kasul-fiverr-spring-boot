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

import com.b127.web.dto.SupportRequest;
import com.b127.web.dto.UpdateUserProfileRequest;
import com.b127.web.entity.Credit;
import com.b127.web.entity.User;
import com.b127.web.services.CreditService;
import com.b127.web.services.TicketService;
import com.b127.web.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CreditService creditService;
	
	@Autowired
	private TicketService ticketService;
	
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String userProfile(Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		model.addAttribute("user", userService.getUser(userId) );
		return "MyProfileClient";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String userProfileUpdate(@ModelAttribute("profile") UpdateUserProfileRequest updateUserProfileRequest, Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		userService.updateUserAndAddProfile(updateUserProfileRequest, userId);
		return "redirect:/user/profile";
	}
	
	@RequestMapping(value = "/credits", method = RequestMethod.GET)
	public String userCredits(Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		model.addAttribute("credits", creditService.getUserCredits(userId) );
		model.addAttribute("credit", new Credit());
		return "CreditProfileClient";
	}
	
	@RequestMapping(value = "/credits", method = RequestMethod.POST)
	public String userCreditAdd(Credit credit,HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		creditService.addAndUpdateCredit(credit, userId);
		return "redirect:/user/credits";
	}
	
	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public String userInbox(Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		model.addAttribute("messages", ticketService.getUserTickets(userId) );
		return "Inbox";
	}
	
	@GetMapping(value = "/ticket/{id}")
	public String userViewMessage(@PathVariable("id") long messageId, Model model) {
		model.addAttribute("ticket", ticketService.getTicketByUser(messageId));
		return "readMessageUser";
	}
	
	@RequestMapping(value = "/support", method = RequestMethod.GET)
	public String userSupport(Model model, HttpSession httpSession) {
		long userId = (long) httpSession.getAttribute("id");
		User user = userService.getUser(userId);
		model.addAttribute("name", user.getFirstname() + " " + user.getLastname() );
		model.addAttribute("email", user.getEmail() );
		return "SpportClient";
	}
	
	@RequestMapping(value = "/support", method = RequestMethod.POST)
	public String userSupportAdd(Model model, HttpSession httpSession, @ModelAttribute("supportRequest") SupportRequest supportRequest ) {
		long userId = (long) httpSession.getAttribute("id");
		ticketService.addTicket(supportRequest, userId);
		return "redirect:/user/inbox";
	}
	

}
