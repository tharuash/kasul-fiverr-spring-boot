package com.b127.web.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b127.web.dto.UpdateUserProfileRequest;
import com.b127.web.entity.MessageQueue;
import com.b127.web.entity.Profile;
import com.b127.web.entity.User;
import com.b127.web.repositories.MessageQueueRepository;
import com.b127.web.repositories.ProfileRepository;
import com.b127.web.repositories.UserRepository;
import com.b127.web.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private MessageQueueRepository messageQueueRepository;
	
	@Override
	public User getUser(long userId) {
		Optional<User> user = userRepository.findById(userId);
		return user.get();
	}

	@Override
	public User updateUserAndAddProfile(UpdateUserProfileRequest updateUserProfileRequest, long userId) {
		
		User currentUser = userRepository.findById(userId).get();
		
		currentUser.setFirstname(updateUserProfileRequest.getFirstname());
		currentUser.setLastname(updateUserProfileRequest.getLastname());
		currentUser.setDob(updateUserProfileRequest.getDob());
		currentUser.setEmail(updateUserProfileRequest.getEmail());
		currentUser.setLastProfileUpdate(LocalDate.now());
		
		User updatedUser = userRepository.save(currentUser);
		
		Profile profile = new Profile();
		//profile.setId( profileRepository.getLatestId() + 1 );
		profile.setUser(updatedUser);
		profile.setEmail(updateUserProfileRequest.getEmail());
		profile.setAddress(updateUserProfileRequest.getAddress());
		profile.setCity(updateUserProfileRequest.getCity());
		profile.setCountry(updateUserProfileRequest.getCountry());
		profile.setDefaultBilling(updateUserProfileRequest.isDefaultBilling());
		profile.setDefaultShipping(updateUserProfileRequest.isDefaultShipping());
		
		Optional<Profile> defaultBillingProfile = profileRepository.findByUserAndIsDefaultBilling(updatedUser, true);
		
		if(updateUserProfileRequest.isDefaultBilling() && defaultBillingProfile.isPresent()) {
			Profile oldProfile = defaultBillingProfile.get();
			oldProfile.setDefaultBilling(false);
			profileRepository.save(oldProfile);
		}
		
		Optional<Profile> defaultShippingProfile = profileRepository.findByUserAndIsDefaultShipping(updatedUser, true);
		
		if(updateUserProfileRequest.isDefaultShipping() && defaultShippingProfile.isPresent()) {
			Profile oldProfile = defaultShippingProfile.get();
			oldProfile.setDefaultShipping(false);
			profileRepository.save(oldProfile);
		}
		
		profileRepository.save(profile);
		
		return updatedUser;
	}

	@Override
	public User updateAdminProfie(User user) {
		User currentUser = userRepository.findById(user.getId()).get();
		
		currentUser.setFirstname(user.getFirstname());
		currentUser.setLastname(user.getLastname());
		currentUser.setEmail(user.getEmail());
		currentUser.setDob(user.getDob());
		currentUser.setAddress(user.getAddress());
		currentUser.setCity(user.getCity());
		currentUser.setCountry(user.getCountry());
		
		
		return userRepository.save(currentUser);
	}

	@Override
	public List<User> getAllUsersByAdmin(long userId) {
		return userRepository.getAllUsers(userId);
	}

	@Override
	public void deleteUser(long userId) {
		User currentUser = userRepository.findById(userId).get();
		
		if(currentUser.getRole().equals("admin")) {
			MessageQueue mq = messageQueueRepository.findByAdminId(currentUser.getId()).get();
			messageQueueRepository.deleteById(mq.getId());
		}
		userRepository.deleteById(userId);	
	}

	@Override
	public User addUser(User user) {
		User savedUser = userRepository.save(user);
		
		if(savedUser.getRole().equals("admin")) {
			MessageQueue msgQueue = new MessageQueue();
			msgQueue.setAdminId(savedUser.getId());
			msgQueue.setCurrentMsgCount(0);
			
			messageQueueRepository.save(msgQueue);
			
		}
		return savedUser;
	}

	@Override
	public List<User> getAdminUsers(long userId) {
		return userRepository.getAdminUsers(userId);
	}
	
	

}
