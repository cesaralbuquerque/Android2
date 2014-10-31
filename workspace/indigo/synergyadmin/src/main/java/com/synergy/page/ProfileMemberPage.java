package com.synergy.page;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.service.CompanyService;

public class ProfileMemberPage extends MemberBasePage {

	@SpringBean
	CompanyService clientService;

	public ProfileMemberPage() {
		Company c = ((QwicketSession) getSession()).getUser();
		UserForm form = new UserForm("edtProfForm", c);
		add(form);
		// add(new PassForm("edtPassForm", c));
	}

	private class UserForm extends Form {

		private String companyName;

		private String email;

		private String www;

		private String phone;

		private Company client = null;

		private String personalPage;

		public UserForm(String id, Company c) {
			super(id);
			this.client = c;
			add(new FeedbackPanel("profFeedback"));
			setModel(new CompoundPropertyModel(this));
			add(new TextField("companyName").setRequired(true));
			add(new TextField("www").setRequired(true));
			add(new TextField("phone"));

			if (c != null) {
				this.companyName = c.getCompanyName();
				this.www = c.getWebsite();
				this.phone = c.getPhone();
				// this.description = c.getListingDescription();
			}

			add(new IFormValidator() {

				public FormComponent[] getDependentFormComponents() {
					// TODO Auto-generated method stub
					return null;
				}

				public void validate(Form form) {

				}

			});
		}

		@Override
		protected void onSubmit() {
			if (!this.hasError()) {
				client.setCompanyName(this.companyName);
				client.setWebsite(this.www);
				client.setPhone(this.phone);
				// client.setListingDescription(this.description);
				// client.setPass(this.pass);
				// client.setPass(Util.criptografar(client.getPass()));
				clientService.save(client);
				((QwicketSession) getSession()).setUser(client);
				info("Profile saved with success");
				// WelcomeMemberPage welcomeMemberPage = new
				// WelcomeMemberPage();
				// welcomeMemberPage.memberFeedback.setVisible(true);
				// welcomeMemberPage.memberFeedback.info("Profile saved with success.");
				// setResponsePage(welcomeMemberPage);
			}
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getPhone() {
			return phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getWww() {
			return www;
		}

		public void setWww(String www) {
			this.www = www;
		}

		public Company getClient() {
			return client;
		}

		public void setClient(Company client) {
			this.client = client;
		}

	}
}
