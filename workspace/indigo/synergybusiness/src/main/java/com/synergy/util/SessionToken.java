package com.synergy.util;

import java.util.Date;

//TODO - Implement Signature Verification
public class SessionToken {

	private String username;

	private Date expirationDate;

	public SessionToken(String username, Date expirationDate) {
		this.username = username;
		this.expirationDate = expirationDate;
	}

	public static SessionToken parse(String session) {
		final String[] split = session.split(";");
		return new SessionToken(split[0], new Date(Long.parseLong(split[1])));
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(username).append(";");
		sb.append(expirationDate.getTime()).append(";");
		return sb.toString();
	}

}
