package com.synergy.page;

import javax.servlet.http.Cookie;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;
import com.synergy.service.AuthenticateService;
import com.synergy.service.CompanyService;
import com.synergy.service.SubscriberService;

public class BasePage extends WebPage {

	final LoginForm loginForm = new LoginForm("loginForm");
	final StaticImage imgDemo = new StaticImage("imgDemo", new Model("img/live-preview-button.gif"));

	public BasePage() {
		QwicketSession session = (QwicketSession) Session.get();
		if (session != null && (session).getUser() != null) {
			// TODO - commented to allow access to public pages when logged in
			// setResponsePage(WelcomeMemberPage.class);
		}
		addTabNavigation();
		setModel(new CompoundPropertyModel(this));
		add(new AdminLoginLink());
		add(loginForm.setVisible(false));
		add(imgDemo);
	}

	private void addTabNavigation() {

	}

	public QwicketSession getQwicketSession() {
		return (QwicketSession) getSession();
	}

	class MenuLink extends BookmarkablePageLink {

		private boolean active;
		private String imgName;
		private final StaticImage image;

		public MenuLink(String id, Class pageClass, String imgName) {
			super(id, pageClass);
			this.imgName = imgName;
			image = new StaticImage(id + "Img", new Model(buildImageName()));
			if (imgName.length() > 0) {
				add(image);
			}
		}

		private String buildImageName() {
			String url = "img/" + imgName;
			if (this.active) {
				url += "-active";
			}
			return url += ".gif";
		}

		public void setActive(boolean active) {
			this.active = active;
			image.setModel(new Model(buildImageName()));
		}

	}

	private static final class LoginForm extends Form {
		@SpringBean
		private SubscriberService service;

		@SpringBean
		private AuthenticateService authenticateService;

		@SpringBean
		private CompanyService companyService;

		private String email = "username";
		private String password = "dummyvalue";
		private Boolean rememberme = Boolean.FALSE;
		FeedbackPanel loginFeedbackPanel = new FeedbackPanel("feedbackLogin");

		public LoginForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(loginFeedbackPanel);
			loginFeedbackPanel.setVisible(false);
			add(new TextField("email"));
			add(new CheckBox("rememberme"));
			add(new PasswordTextField("password"));
			// add(new BookmarkablePageLink("forgotPass",
			// ClientForgotPassword.class));
		}

		@Override
		public void onSubmit() {
			String sessionId = authenticateService.authenticate(getEmail(), getPassword());
			if (sessionId == null) {
				loginFeedbackPanel.setVisible(true);
				error("Invalid email or password.");
			} else {
				Subscriber user = authenticateService.validateSessionId(sessionId);
//				if (user.getConfirmStatus().equals(Subscriber.WAITING_CONFIRMATION)) {
//					loginFeedbackPanel.setVisible(true);
//					error("Please check your email to confirm this account.");
//					return;
//				}

				// if (!user.getAccountActive()) {
				// loginFeedbackPanel.setVisible(true);
				// error("This account is not active.");
				// return;
				// }

				QwicketSession session = ((BasePage) getPage()).getQwicketSession();
				session.setUser(user.getCompany());

				if (getRememberme()) {
					((WebResponse) getRequestCycle().getResponse()).addCookie(new Cookie("SYN_MEMBER_LOGIN", sessionId));
				}

				setResponsePage(new WelcomeMemberPage());
			}

		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String s) {
			email = s;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String s) {
			password = s;
		}

		public void setRememberme(Boolean rememberme) {
			this.rememberme = rememberme;
		}

		public Boolean getRememberme() {
			return rememberme;
		}
	}

	private class AdminLoginLink extends Link {

		public AdminLoginLink() {
			super("adminloginLink");
			add(new StaticImage("adminImg", new Model("img/admin-login-button.gif")));
		}

		@Override
		public void onClick() {
			this.setVisible(false);
			loginForm.setVisible(true);
		}

	}
}
