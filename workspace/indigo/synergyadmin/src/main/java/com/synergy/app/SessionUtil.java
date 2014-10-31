package com.synergy.app;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;

import com.synergy.model.Subscriber;
import com.synergy.service.AuthenticateService;

public class SessionUtil {

	public static final String SYNERGY_CHAT_COOKIE = "SynergyChat";

	public static final String SYNERGY_CHAT_SESSION_ID = "SynergyChat_SessionId";

	public static String createSessionId(HttpServletRequest request, Subscriber subscriber) {
		WebApplicationContext springContext = (WebApplicationContext) request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		AuthenticateService authService = (AuthenticateService) springContext.getBean("authenticateService");
		final String sessionId = authService.generateSessionId(subscriber);
		request.getSession().setAttribute(SYNERGY_CHAT_SESSION_ID, sessionId);
		return sessionId;
	}

	public static void destroySessionIdAndCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie myCookie = getCookie(request);
		if (myCookie != null) {
			myCookie.setMaxAge(0);
			myCookie.setValue(null);
			response.addCookie(myCookie);
		}
		request.getSession().invalidate();
	}

	public static String getSessionId(HttpServletRequest request) {
		// first search in the session
		String sessionId = (String) request.getSession().getAttribute(SYNERGY_CHAT_SESSION_ID);
		Cookie myCookie = null;
		if (sessionId == null) {
			myCookie = getCookie(request);
			if (myCookie != null) {
				sessionId = myCookie.getValue();
			}
		}
		if (sessionId != null) {
			WebApplicationContext springContext = (WebApplicationContext) request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			AuthenticateService authService = (AuthenticateService) springContext.getBean("authenticateService");
			Subscriber s = authService.validateSessionId(sessionId);
			if (!authService.isValidSessionId(sessionId)) {
				request.getSession().invalidate();
				sessionId = null;
				if (myCookie != null) {
					myCookie.setValue(null);
				}
			}
		}
		return sessionId;
	}

	private static Cookie getCookie(HttpServletRequest request) {
		Cookie myCookie = null;
		// if it doesn't have a session, try to find a cookie
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(SYNERGY_CHAT_COOKIE)) {
					myCookie = cookies[i];
					break;
				}
			}
		}
		return myCookie;
	}

}
