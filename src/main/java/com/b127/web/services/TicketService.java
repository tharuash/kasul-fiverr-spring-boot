package com.b127.web.services;

import java.util.List;

import com.b127.web.dto.SupportRequest;
import com.b127.web.dto.TicketReply;
import com.b127.web.entity.Ticket;

public interface TicketService {
	
	List<Ticket> getUserTickets(long userId);
	
	void UpdateTicketAsSeenByUser(long ticketId);
	
	void deleteTicket(long ticketId);
	
	Ticket addTicket(SupportRequest supportRequest, long userId);
	
	List<Ticket> getAdminTickets(long userId);
	
	void UpdateTicketAsSeenByAdmin(long ticketId, long userId);
	
	void UpdateTicketReply(TicketReply ticketReply, long userId);
	
	Ticket getTicketByAdmin(Long ticketId, long userId);
	
	Ticket getTicketByUser(Long ticketId);

}
