package com.b127.web.dto;

import com.b127.web.entity.Profile;
import com.b127.web.entity.User;

public class UserDashboard {

	private User user;
	private int readMessages;
	private int unreadMessages;
	private Profile shippingProfile;
	private Profile billingProfile;

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

	public Profile getShippingProfile() {
		return shippingProfile;
	}

	public void setShippingProfile(Profile shippingProfile) {
		this.shippingProfile = shippingProfile;
	}

	public Profile getBillingProfile() {
		return billingProfile;
	}

	public void setBillingProfile(Profile billingProfile) {
		this.billingProfile = billingProfile;
	}

}
