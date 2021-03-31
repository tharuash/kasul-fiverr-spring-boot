package com.b127.web.services;

import com.b127.web.dto.LoginRequest;
import com.b127.web.dto.LoginResponse;
import com.b127.web.entity.User;

public interface AuthService {
	
	LoginResponse loginUser(LoginRequest loginRequest);
	
	LoginResponse registerUser(User user);
	
	User registerAdmin(User user);
}	
