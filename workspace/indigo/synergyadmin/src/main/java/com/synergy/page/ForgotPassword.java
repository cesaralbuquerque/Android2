package com.synergy.page;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.service.SubscriberService;

public class ForgotPassword extends BasePageRegistering {

	@SpringBean
	SubscriberService subscriberService;

	FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");

	public ForgotPassword() {
		add(feedbackPanel);
		add(new ClientForm("form"));
	}

	private class ClientForm extends Form {

		private String username;

		public ClientForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));			
			add(new TextField("username").setRequired(true));
		}

		@Override
		protected void onSubmit() {
			if (subscriberService.emailPassword(username)) {
				feedbackPanel.info("Your password has been emailed to you.  Check your email");
			} else {
				feedbackPanel.error("No client account was found for that email.  Check the email and try again.");
			}
			// setResponsePage(HomePage.class);
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUsername() {
			return username;
		}

	}

}
