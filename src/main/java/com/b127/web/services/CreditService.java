package com.b127.web.services;

import java.util.List;

import com.b127.web.entity.Credit;

public interface CreditService {
	
	Credit addAndUpdateCredit(Credit credit, long userId);
	
	void deleteCredit(long creditId);
	
	List<Credit> getUserCredits(long userId);
}
