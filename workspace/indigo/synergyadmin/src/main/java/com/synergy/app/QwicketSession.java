// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 1/15/2009 10:11:19 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   QwicketSession.java

package com.synergy.app;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;

import com.synergy.model.Company;

public class QwicketSession extends WebSession {

	public QwicketSession(Request request) {
		super(request);
	}

	public void addAttribute(String key, Object value) {
		setAttribute(key, value);
	}

	public Object findAttribute(String key) {
		return getAttribute(key);
	}

	public void deleteAttribute(String key) {
		removeAttribute(key);
	}

	public Company getUser() {
		return user;
	}

	public void setUser(Company sessionUser) {
		user = sessionUser;
	}

	public static QwicketSession session() {
		return (QwicketSession) Session.get();
	}

	private Company user;

}