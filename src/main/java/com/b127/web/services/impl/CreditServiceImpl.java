package com.b127.web.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b127.web.entity.Credit;
import com.b127.web.entity.User;
import com.b127.web.repositories.CreditRepository;
import com.b127.web.repositories.UserRepository;
import com.b127.web.services.CreditService;

@Service
public class CreditServiceImpl implements CreditService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CreditRepository creditRepository;
	
	@Override
	public Credit addAndUpdateCredit(Credit credit, long userId) {
		
		User currentUser = userRepository.findById(userId).get();
		credit.setUser(currentUser);
		//credit.setId(creditRepository.getLatestId() + 1);
		
		Optional<Credit> defaultCreditCard = creditRepository.findByUserAndIsDefault(currentUser, true);
		
		if(credit.getIsDefault() && defaultCreditCard.isPresent()) {
			Credit oldDefaultCredit = defaultCreditCard.get();
			oldDefaultCredit.setIsDefault(false);
			creditRepository.save(oldDefaultCredit);
		}

		return creditRepository.save(credit);
	}

	@Override
	public void deleteCredit(long creditId) {
		creditRepository.deleteById(creditId);
	}

	@Override
	public List<Credit> getUserCredits(long userId) {
		User currentUser = userRepository.findById(userId).get();
		
		return creditRepository.findByUser(currentUser);
	}

}
