package com.b127.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.b127.web.services.TicketService;

@Controller
@RequestMapping("/messages")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@GetMapping(value = "/user/delete/{id}")
	public String deleteMessage(@PathVariable("id") long messageId) {
		ticketService.deleteTicket(messageId);
		
		return "redirect:/user/inbox";
	}
	
	@GetMapping(value = "/admin/delete/{id}")
	public String adminDeleteMessage(@PathVariable("id") long messageId) {
		ticketService.deleteTicket(messageId);
		
		return "redirect:/admin/inbox";
	}
	

}
