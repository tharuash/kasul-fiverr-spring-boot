package com.b127.web.dto;

import java.util.List;

import com.b127.web.entity.User;

public class AdminDashboard {

	private User user;
	private int readMessages;
	private int unreadMessages;
	private int registeredUsersCount;
	private List<User> registeredUsers;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getReadMessages() {
		return readMessages;
	}

	public void setReadMessages(int readMessages) {
		this.readMessages = readMessages;
	}

	public int getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(int unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

	public int getRegisteredUsersCount() {
		return registeredUsersCount;
	}

	public void setRegisteredUsersCount(int registeredUsersCount) {
		this.registeredUsersCount = registeredUsersCount;
	}

	public List<User> getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(List<User> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}

}
