package com.b127.web.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import com.b127.web.dto.SupportRequest;
import com.b127.web.dto.TicketReply;
import com.b127.web.entity.MessageQueue;
import com.b127.web.entity.Ticket;
import com.b127.web.entity.User;
import com.b127.web.repositories.MessageQueueRepository;
import com.b127.web.repositories.TicketRepository;
import com.b127.web.repositories.UserRepository;
import com.b127.web.services.TicketService;

@Service
public class TicketServiceImpl implements TicketService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private MessageQueueRepository messageQueueRepository;
	
	@Override
	public List<Ticket> getUserTickets(long userId) {
		User currentUser = userRepository.findById(userId).get();
		return ticketRepository.getUserUnreadTickets(currentUser);
	}

	@Override
	public void UpdateTicketAsSeenByUser(long ticketId) {
		ticketRepository.updateTicketAsSeenByUser(ticketId);
		
	}

	@Override
	public void deleteTicket(long ticketId) {
		ticketRepository.deleteById(ticketId);
		
	}

	@Override
	public Ticket addTicket(SupportRequest supportRequest, long userId) {
		User currentUser = userRepository.findById(userId).get();
		MessageQueue currentQueue = messageQueueRepository.getMinimumMsgCountQueue().get(0);
		User ticketReceivingAdmin = userRepository.findById(currentQueue.getAdminId()).get();
		
		Ticket ticket = new Ticket();
		ticket.setSender(currentUser);
		ticket.setSubject(supportRequest.getSubject());
		ticket.setMessage(supportRequest.getMessage());
		ticket.setDateAdded(LocalDate.now());
		ticket.setReceivedAdmin(ticketReceivingAdmin);
		Ticket savedTicket = ticketRepository.save(ticket);
		
		currentQueue.setCurrentMsgCount( currentQueue.getCurrentMsgCount() + 1);
		messageQueueRepository.save(currentQueue);
		
		return savedTicket;
	}

	@Override
	public List<Ticket> getAdminTickets(long userId) {
		User currentUser = userRepository.findById(userId).get();
		return ticketRepository.getAdminUnreadTickets(currentUser);
	}

	@Override
	public void UpdateTicketAsSeenByAdmin(long ticketId, long userId) {
		MessageQueue currentQueue = messageQueueRepository.findById(userId).get();
		currentQueue.setCurrentMsgCount(currentQueue.getCurrentMsgCount() - 1);
		messageQueueRepository.save(currentQueue);
		ticketRepository.updateTicketAsSeenByAdmin(ticketId);
	}

	@Override
	public void UpdateTicketReply(TicketReply ticketReply, long userId) {
		Optional<Ticket> ticket = ticketRepository.findById(ticketReply.getTicketId());
		Optional<MessageQueue> messageQueue = messageQueueRepository.findByAdminId(userId);
		
		if(ticket.isPresent()) {
			Ticket oldTicket = ticket.get();
			oldTicket.setReply(ticketReply.getReply());
			oldTicket.setDateReplied(LocalDate.now());
			
			ticketRepository.save(oldTicket);
		}
		
		if(messageQueue.isPresent()) {
			MessageQueue mq = messageQueue.get();
			mq.setCurrentMsgCount(mq.getCurrentMsgCount() - 1);
			
			messageQueueRepository.save(mq);
		}
	}

	@Override
	public Ticket getTicketByAdmin(Long ticketId, long userId) {
		Ticket ticket = ticketRepository.findById(ticketId).get();
		ticket.setIsMessageSeenByAdmin(true);
		MessageQueue currentQueue = messageQueueRepository.findByAdminId(userId).get();
		currentQueue.setCurrentMsgCount(currentQueue.getCurrentMsgCount() - 1);
		messageQueueRepository.save(currentQueue);
		return ticketRepository.save(ticket);	
	}

	@Override
	public Ticket getTicketByUser(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId).get();
		ticket.setIsReplySeenByUser(true);
		return ticketRepository.save(ticket);
	}
	
	

}
