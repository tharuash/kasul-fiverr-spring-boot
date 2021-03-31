package com.b127.web;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.b127.web.entity.User;
import com.b127.web.repositories.UserRepository;
import com.b127.web.services.AuthService;
import com.b127.web.services.UserService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public void run(String... args) throws Exception {
		
		Optional<User> defaultUser = userRepository.findById(1L);
		
		if(!defaultUser.isPresent()) {
			User user = new User();
			user.setId(1L);
			user.setFirstname("John");
			user.setLastname("Doe");
			user.setEmail("john@doe.com");
			user.setCity("Toronto");
			user.setCountry("Canada");
			user.setDob(LocalDate.now());
			user.setAddress("564 Cross rd");
			user.setUsername("john");
			user.setPassword("1234");
			
			authService.registerAdmin(user);
		}
		
		
		
	}

}
