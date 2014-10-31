package com.synergy.page;

import javax.servlet.http.Cookie;

import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.app.SessionUtil;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;
import com.synergy.service.AuthenticateService;
import com.synergy.service.BrainTreeService;
import com.synergy.service.CompanyService;
import com.synergy.service.SubscriberService;
import com.synergy.util.CipherUtil;

public class MemberAdminLogin extends BasePageMemberAdminLogin {

	@SpringBean
	private SubscriberService service;

	@SpringBean
	private AuthenticateService authenticateService;

	@SpringBean
	private BrainTreeService braintreeService;

	@SpringBean
	private CompanyService companyService;

	String username = null;

	public MemberAdminLogin() {
		this(null);
	}

	public MemberAdminLogin(PageParameters parameters) {
		super("Member Admin Login");
		setModel(new CompoundPropertyModel(this));

		if (parameters != null) {
			String[] activeAccount = (String[]) parameters.get("actAccount");
			if (activeAccount != null && activeAccount.length > 0) {
				if (true == Boolean.valueOf(activeAccount[0])) {
					String clientId = ((String[]) parameters.get("cid"))[0].toString();
					try {
						clientId = CipherUtil.decrypt(clientId);
					} catch (Exception e) {
						throw new IllegalArgumentException("ERROR: Account not found");
					}
					Subscriber client = service.find(Long.parseLong(clientId));
					if (client == null) {
						throw new IllegalArgumentException("ERROR: Account not found");
					}
					username = client.getEmail();
					client.getCompany().setConfirmStatus(Company.CONFIRMED);
					service.save(client);

				}
			} else {
				String[] sid = (String[]) parameters.get("sid");
				if (sid != null && sid.length > 0) {
					validateSessionId(sid[0]);
				}
			}
		} else {
			// if no parameters, then check cookir
			final Cookie cookie = ((WebRequest) getRequestCycle().getRequest()).getCookie(SessionUtil.SYNERGY_CHAT_COOKIE);
			if (cookie != null) {
				validateSessionId(cookie.getValue());
			}
		}
		add(new LoginForm("loginForm"));
		final MsgBox msgBox = new MsgBox(username);
		msgBox.setVisible(username != null);
		add(msgBox);
		getRequestCycle().setRequestTarget(new RedirectRequestTarget("ui"));
	}

	protected void validateSessionId(String sessionId) {
		Subscriber user = authenticateService.validateSessionId(sessionId);
		if (user != null) {
			QwicketSession session = (QwicketSession) Session.get();
			session.setUser(user.getCompany());
			setResponsePage(new WelcomeMemberPage());
		}
	}

	private final class LoginForm extends Form {
		private String email = "username";
		private String password = "dummyvalue";
		private Boolean rememberme = Boolean.FALSE;
		FeedbackPanel loginFeedbackPanel = new FeedbackPanel("feedbackLogin");

		public LoginForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(loginFeedbackPanel);
			loginFeedbackPanel.setVisible(false);
			email = username;
			final TextField usernameField = new TextField("email");
			add(usernameField);
			add(new PasswordTextField("password"));
		}

		@Override
		public void onSubmit() {
			String sessionId = authenticateService.authenticate(getEmail(), getPassword());

			if (sessionId == null) {
				loginFeedbackPanel.setVisible(true);
				error("Invalid email or password.");
			} else {
				Subscriber user = authenticateService.validateSessionId(sessionId);
				// if
				// (user.getConfirmStatus().equals(Subscriber.WAITING_CONFIRMATION))
				// {
				// loginFeedbackPanel.setVisible(true);
				// error("Please check your email to confirm this client.");
				// return;
				// }
				// if (!user.getAccountActive()) {
				// loginFeedbackPanel.setVisible(true);
				// error("This account is not active.");
				// return;
				// }
				QwicketSession session = (QwicketSession) Session.get();
				session.setUser(user.getCompany());

				// if (getRememberme()) {
				// ((WebResponse) getRequestCycle().getResponse()).addCookie(new
				// Cookie("SYN_MEMBER_LOGIN", getEmail() + ";" +
				// getPassword()));
				// }

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

	private static class MsgBox extends Form {

		public MsgBox(String username) {
			super("msgBox");
			add(new Label("lblUserName", username));
		}
	}

}
