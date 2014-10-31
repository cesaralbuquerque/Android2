package com.synergy.util;

import java.util.Date;

import com.synergy.model.Subscriber;

public class SessionIdUtil {

	private static SessionIdUtil instance;

	private SessionIdUtil() {
	}

	public static SessionIdUtil getInstace() {
		if (instance == null) {
			instance = new SessionIdUtil();
		}
		return instance;
	}

	public String generateSessionId(SessionToken token) throws Exception {
		return CipherUtil.encrypt(token.toString());
	}

	public SessionToken decodeSessionId(String sessionId) throws Exception {
		return SessionToken.parse(CipherUtil.decrypt(sessionId));
	}

}
