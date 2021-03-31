package com.b127.web.services;

import java.util.List;

import com.b127.web.dto.UpdateUserProfileRequest;
import com.b127.web.entity.User;

public interface UserService {
	
	User getUser(long userId);
	
	User updateUserAndAddProfile(UpdateUserProfileRequest updateUserProfileRequest, long userId);
	
	User updateAdminProfie(User user);
	
	List<User> getAllUsersByAdmin(long userId);
	
	void deleteUser(long userId);
	
	User addUser(User user);
	
	List<User> getAdminUsers(long userId);

}
