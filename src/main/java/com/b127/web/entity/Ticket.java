package com.b127.web.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String subject;
	private String message;
	private LocalDate dateAdded;
	private String reply;
	private LocalDate dateReplied;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", insertable = true, updatable = true)
	private User sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", insertable = true, updatable = true)
	private User receivedAdmin;
	private boolean isMessageSeenByAdmin;
	private boolean isReplySeenByUser;

	public Ticket() {

	}

	public Ticket(long id, String subject, String message, LocalDate dateAdded, String reply, LocalDate dateReplied,
			User sender, User receivedAdmin, boolean isMessageSeenByAdmin, boolean isReplySeenByUser) {
		super();
		this.id = id;
		this.subject = subject;
		this.message = message;
		this.dateAdded = dateAdded;
		this.reply = reply;
		this.dateReplied = dateReplied;
		this.sender = sender;
		this.receivedAdmin = receivedAdmin;
		this.isMessageSeenByAdmin = isMessageSeenByAdmin;
		this.isReplySeenByUser = isReplySeenByUser;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public LocalDate getDateReplied() {
		return dateReplied;
	}

	public void setDateReplied(LocalDate dateReplied) {
		this.dateReplied = dateReplied;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceivedAdmin() {
		return receivedAdmin;
	}

	public void setReceivedAdmin(User receivedAdmin) {
		this.receivedAdmin = receivedAdmin;
	}

	public boolean getIsMessageSeenByAdmin() {
		return isMessageSeenByAdmin;
	}

	public void setIsMessageSeenByAdmin(boolean isMessageSeenByAdmin) {
		this.isMessageSeenByAdmin = isMessageSeenByAdmin;
	}

	public boolean getIsReplySeenByUser() {
		return isReplySeenByUser;
	}

	public void setIsReplySeenByUser(boolean isReplySeenByUser) {
		this.isReplySeenByUser = isReplySeenByUser;
	}

}
