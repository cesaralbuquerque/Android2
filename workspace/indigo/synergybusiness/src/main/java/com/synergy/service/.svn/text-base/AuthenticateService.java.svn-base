package com.synergy.service;

import java.util.Date;

import com.synergy.model.Subscriber;

public interface AuthenticateService {

	public String generateSessionId(Subscriber subscriber);
	
	public String generateSessionId(Subscriber subscriber, Date expirationDate);
	
	public String authenticate(String username, String password);

	public String authenticateDate(String username, String password, Date expirationDate);
	
	public Subscriber validateSessionId(String sessionId);
	
	public boolean isValidSessionId(String sessionId);
}
