package com.b127.web.dto;

public class LoginResponse {

	private boolean isLoginSuccess;
	private String message;
	private UserDashboard userDashboard;
	private AdminDashboard adminDashboard;
	private boolean isAdmin;

	public boolean isLoginSuccess() {
		return isLoginSuccess;
	}

	public void setLoginSuccess(boolean isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserDashboard getUserDashboard() {
		return userDashboard;
	}

	public void setUserDashboard(UserDashboard userDashboard) {
		this.userDashboard = userDashboard;
	}
	
	public AdminDashboard getAdminDashboard() {
		return adminDashboard;
	}
	
	public void setAdminDashboard(AdminDashboard adminDashboard) {
		this.adminDashboard = adminDashboard;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
