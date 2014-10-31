package com.synergy.page;

import javax.servlet.http.Cookie;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.app.SessionUtil;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;
import com.synergy.service.AuthenticateService;
import com.synergy.service.CompanyService;
import com.synergy.service.GroupService;
import com.synergy.service.SubscriberService;

public class MemberBasePage extends WebPage {

	@SpringBean
	CompanyService clientService;

	@SpringBean
	AuthenticateService authenticateService;

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	GroupService groupService;

	private Company client;

	FeedbackPanel memberFeedback = new FeedbackPanel("memberFeedback");

	// final Link wizardNavigate;

	protected void validateSessionId(String sessionId) {
		Subscriber user = getUserFromSessionId(sessionId);
		if (subscriberService.isAdmin(user, user.getCompany())) {
			addUserToSession(user);
		} else {
			throw new RestartResponseException(PermissionDeniedMemberPage.class);
		}
	}

	private void addUserToSession(Subscriber user) {
		if (user != null) {
			QwicketSession session = (QwicketSession) Session.get();
			session.setUser(user.getCompany());
		}
	}

	private Subscriber getUserFromSessionId(String sessionId) {
		Subscriber user = authenticateService.validateSessionId(sessionId);
		return user;
	}

	public MemberBasePage() {
		checkSessionPermission();

		add(new LogoutLink());
		add(new Label("usernameLbl", ""/*
										 * ((QwicketSession)
										 * getSession()).getUser
										 * ().getCompanyName()
										 */));
		add(memberFeedback);
		memberFeedback.setVisible(false);
		addTabNavigation();
	}

	protected void checkSessionPermission() {
		String sid = getRequest().getParameter("sid");
		if (sid != null && sid.length() > 0) {
			validateSessionId(sid);
		} else {
			ServletWebRequest servletWebRequest = (ServletWebRequest) getRequest();
			final String sessionId = SessionUtil.getSessionId(servletWebRequest.getHttpServletRequest());
			if (sessionId != null) {
				// final Cookie cookie = ((WebRequest)
				// getRequestCycle().getRequest()).getCookie(SessionUtil.SYNERGY_CHAT_COOKIE);
				// if (cookie != null) {
				validateSessionId(sessionId);
			}
		}
		final Company user = ((QwicketSession) QwicketSession.session()).getUser();
		if (user == null) {
			throw new RestartResponseException(MemberAdminLogin.class);
		}
	}

	protected Company loadClient() {
		if (client == null) {
			client = ((QwicketSession) QwicketSession.session()).getUser();
			if (client != null) {
				client = clientService.find(client.getId());
			}
		}
		return client;
	}

	protected void addTabNavigation() {
		add(new BookmarkablePageLink("profileLink", ProfileMemberPage.class));
		add(new BookmarkablePageLink("contactsLink", ContactsMemberPage.class));
		add(new BookmarkablePageLink("paymentLink", CurrentPlan.class));
		add(new BookmarkablePageLink("logsLink", ChatLogMemberPage.class));

	}

	private static class LogoutLink extends Link {
		public LogoutLink() {
			super("logout");
		}

		@Override
		public void onClick() {
			Session.get().invalidateNow();
			Cookie ret = ((WebRequest) getRequestCycle().getRequest()).getCookie("SynergyChat");
			if (ret != null) {
				((WebResponse) getRequestCycle().getResponse()).clearCookie(ret);
			}
			// setResponsePage(Application.get().getHomePage());
			setResponsePage(MemberAdminLogin.class);
		}

		@Override
		public boolean isVisible() {
			return QwicketSession.session().getUser() != null;
		}

	}

}
