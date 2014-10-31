package com.synergy.app;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;

import com.synergy.model.Subscriber;
import com.synergy.service.AuthenticateService;
import com.synergy.util.LocalEncrypter;

public class AuthenticateServletEncrypted extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache");
		WebApplicationContext springContext = (WebApplicationContext) req.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		AuthenticateService authService = (AuthenticateService) springContext.getBean("authenticateService");
		String checkSession = req.getParameter("checkSession");
		if (checkSession != null) {
			Subscriber sub = authService.validateSessionId(checkSession);
			if (sub != null) {
				resp.getOutputStream().print("TRUE");
			} else {
				resp.getOutputStream().print("FALSE");
			}
			return;
		}
		try {
			String username = LocalEncrypter.decryptString(req.getParameter("username"));
			String password = LocalEncrypter.decryptString(req.getParameter("password"));
			if (username != null && password != null) {
				long day = 1000 * 60 * 60 * 24;
				String sessionId = authService.authenticateDate(username, password, new Date(new Date().getTime() + day));
				if (sessionId != null) {
					resp.getOutputStream().print(sessionId);
				} else {
					resp.getOutputStream().print("NULL");
				}
			} else {
				resp.getOutputStream().print("Username or password are null.");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
