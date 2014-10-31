package com.synergy.app;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;

import com.synergy.model.Subscriber;
import com.synergy.service.AuthenticateService;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache");
		WebApplicationContext springContext = (WebApplicationContext) req.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		AuthenticateService authService = (AuthenticateService) springContext.getBean("authenticateService");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String page = req.getParameter("page");
		String remember = req.getParameter("remember");
		if (username != null && password != null) {
			long day = 1000 * 60 * 60 * 24;
			if ("TRUE".equalsIgnoreCase(remember)) {
				//if remember, then set the cookie to 3 months
				day = day * 90;
			}
			final Date expiry = new Date(new Date().getTime() + day);
			String sessionId = authService.authenticateDate(username, password, expiry);
			if (sessionId != null) {
				Cookie cookie = new Cookie("SynergyChat", sessionId);
				cookie.setMaxAge((int) expiry.getTime());
				resp.addCookie(cookie);
				req.getRequestDispatcher("VMail.jsp").forward(req, resp);
			} else {
				resp.getOutputStream().print("NULL");
			}
		} else {
			resp.getOutputStream().print("Username or password are null.");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
