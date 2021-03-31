package com.b127.web.services.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b127.web.dto.AdminDashboard;
import com.b127.web.dto.LoginRequest;
import com.b127.web.dto.LoginResponse;
import com.b127.web.dto.UserDashboard;
import com.b127.web.entity.MessageQueue;
import com.b127.web.entity.Profile;
import com.b127.web.entity.User;
import com.b127.web.repositories.MessageQueueRepository;
import com.b127.web.repositories.ProfileRepository;
import com.b127.web.repositories.UserRepository;
import com.b127.web.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private MessageQueueRepository messageQueueRepository;
	
	@Override
	public LoginResponse loginUser(LoginRequest loginRequest) {
		LoginResponse loginResponse = new LoginResponse();
		Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
		
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(loginRequest.getPassword().equals(user.getPassword())) {
				if(user.getRole().equals("admin")) {
					AdminDashboard adminDashboard = new AdminDashboard();
					adminDashboard.setUser(user);
					adminDashboard.setReadMessages(userRepository.countReadMessagesByAdmin(user.getId()));
					adminDashboard.setUnreadMessages(userRepository.countUnreadMessagesByAdmin(user.getId()));
					adminDashboard.setRegisteredUsersCount(userRepository.countRegisteredUsersByAdmin());
					adminDashboard.setRegisteredUsers(userRepository.findByRole("user"));
					
					loginResponse.setLoginSuccess(true);
					loginResponse.setMessage("Successfully Logged In");
					loginResponse.setAdmin(true);
					loginResponse.setAdminDashboard(adminDashboard);
					
				}
				
				if(user.getRole().equals("user")) {
					UserDashboard userDashboard = new UserDashboard();
					userDashboard.setUser(user);
					userDashboard.setReadMessages(userRepository.countReadMessagesByUser(user.getId()));
					userDashboard.setUnreadMessages(userRepository.countUnreadMessagesByUser(user.getId()));
					
					
					Optional<Profile> defaultBillingProfile = profileRepository.getUserDefaultBillingProfile(user);
					if( defaultBillingProfile.isPresent() ) {
						userDashboard.setBillingProfile(defaultBillingProfile.get());
					}
					
					Optional<Profile> defaultShippingProfile = profileRepository.getUserDefaultShippingProfile(user);
					if( defaultShippingProfile.isPresent()) {
						userDashboard.setShippingProfile(defaultShippingProfile.get());
					}
					
					loginResponse.setLoginSuccess(true);
					loginResponse.setMessage("Successfully Logged In");
					loginResponse.setAdmin(false);
					loginResponse.setUserDashboard(userDashboard);
					
					user.setLastLogged(LocalDate.now());
					userRepository.save(user);
				}
				
			}else {
				loginResponse.setLoginSuccess(false);
				loginResponse.setMessage("Given username and password doesn't match.");
			}
		}else {
			loginResponse.setLoginSuccess(false);
			loginResponse.setMessage("The given username has not registered yet.");
		}
		return loginResponse;
	}

	@Override
	public LoginResponse registerUser(User user) {
		LoginResponse loginResponse = new LoginResponse();
		
		if(!userRepository.existsByUsername(user.getUsername())) {
			user.setId(userRepository.getLatestId() + 1);
			user.setLastLogged(LocalDate.now());
			user.setRole("user");
			User savedUser = userRepository.save(user);
			
			UserDashboard userDashboard = new UserDashboard();
			userDashboard.setUser(savedUser);
			userDashboard.setReadMessages(userRepository.countReadMessagesByUser(savedUser.getId()));
			userDashboard.setUnreadMessages(userRepository.countUnreadMessagesByUser(savedUser.getId()));
			
			Optional<Profile> defaultBillingProfile = profileRepository.getUserDefaultBillingProfile(user);
			if( defaultBillingProfile.isPresent() ) {
				userDashboard.setBillingProfile(defaultBillingProfile.get());
			}
			
			Optional<Profile> defaultShippingProfile = profileRepository.getUserDefaultShippingProfile(user);
			if( defaultShippingProfile.isPresent()) {
				userDashboard.setShippingProfile(defaultShippingProfile.get());
			}
			
			loginResponse.setLoginSuccess(true);
			loginResponse.setMessage("Successfully Logged In");
			loginResponse.setAdmin(false);
			loginResponse.setUserDashboard(userDashboard);
			
		} else {
			loginResponse.setLoginSuccess(false);
			loginResponse.setMessage("Username already registered.");
		}
		
		return loginResponse;
	}

	@Override
	public User registerAdmin(User user) {
		//user.setId(userRepository.getLatestId() + 1);
		user.setRole("admin");
		User savedUser = userRepository.save(user);
		
		MessageQueue msgQueue = new MessageQueue();
		msgQueue.setAdminId(savedUser.getId());
		msgQueue.setCurrentMsgCount(0);
			
		messageQueueRepository.save(msgQueue);
			
		return savedUser;
	}
}
