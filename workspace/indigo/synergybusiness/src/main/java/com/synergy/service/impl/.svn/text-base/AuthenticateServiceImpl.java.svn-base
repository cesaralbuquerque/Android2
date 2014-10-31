package com.synergy.service.impl;

import java.util.Date;

import javax.jws.WebService;

import com.synergy.model.Subscriber;
import com.synergy.service.AuthenticateService;
import com.synergy.service.SubscriberService;
import com.synergy.util.SessionIdUtil;
import com.synergy.util.SessionToken;

public class AuthenticateServiceImpl implements AuthenticateService {

	private SubscriberService subscriberService;

	public AuthenticateServiceImpl() {
	}

	public String generateSessionId(Subscriber subscriber) {
		long hour = 1000 * 60 * 15;
		final Date date = new Date(new Date().getTime() + hour);
		return generateSessionId(subscriber, date);
	}

	public String generateSessionId(Subscriber subscriber, Date expirationDate) {
		try {
			return SessionIdUtil.getInstace().generateSessionId(new SessionToken(subscriber.getEmail(), expirationDate));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String authenticate(String username, String password) {
		long day = 1000 * 60 * 15;
		final Date date = new Date(new Date().getTime() + day);
		return authenticateDate(username, password, date);
	}

	public String authenticateDate(String username, String password, Date expirationDate) {
		Subscriber sub = subscriberService.authenticate(username, password);
		if (sub == null) {
			return null;
		}
		return generateSessionId(sub, expirationDate);
	}

	public Subscriber validateSessionId(String sessionId) {
		final SessionToken token = getTokenFromSession(sessionId);
		if (token == null) {
			// Session expired
			return null;
		}
		return subscriberService.getByEmail(token.getUsername());
	}

	protected SessionToken getTokenFromSession(String sessionId) {
		SessionToken token;
		try {
			token = SessionIdUtil.getInstace().decodeSessionId(sessionId);
			if (token.getExpirationDate().before(new Date())) {
				// Session expired
				return null;
			}
			return token;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isValidSessionId(String sessionId) {
		return getTokenFromSession(sessionId) != null;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

}
