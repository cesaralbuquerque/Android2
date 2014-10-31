package com.synergy.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;
import com.synergy.service.CompanyService;
import com.synergy.service.GroupService;
import com.synergy.service.ProductService;
import com.synergy.service.SubscriberService;
import com.synergy.util.CipherUtil;
import com.synergy.util.Util;
import com.synergy.util.UtilOld;

public class JoinCompanyPage extends BasePageRegistering {

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	CompanyService companyService;

	@SpringBean
	GroupService groupService;

	@SpringBean
	ProductService productService;

	private Subscriber subscriber;

	private Company company;

	private List<Group> groups = new ArrayList<Group>();

	private List<Product> products = new ArrayList<Product>();

	FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");

	final RegistrationForm clientForm = new RegistrationForm("regForm");

	final AcceptInviteForm acceptForm = new AcceptInviteForm("acpForm");
	private AjaxLink loginLink;

	public JoinCompanyPage() {
		add(feedbackPanel);
		add(clientForm);
		add(acceptForm);
		loginLink = new AjaxLink("btLogin") {

			@Override
			public void onClick(AjaxRequestTarget arg0) {
				getRequestCycle().setRequestTarget(new RedirectRequestTarget("ui"));
			}

		};
		add(loginLink);
		loginLink.setVisible(false);
		if (!loadParams()) {
			error("Invalid invite key");
			clientForm.setVisible(false);
			acceptForm.setVisible(false);
			return;
		}
		// if the company is the same, so it's a new user
		if (company.equals(subscriber.getCompany())) {
			// hide the form to accept the invite
			acceptForm.setVisible(false);
			clientForm.setEmail(subscriber.getEmail());
			clientForm.setName(subscriber.getName());

		} else {
			acceptForm.setCompName(company.getCompanyName());
			// hide the form to finish the registration
			clientForm.setVisible(false);
		}
	}

	private boolean loadParams() {
		String sid = getRequest().getParameter("invKey");
		try {
			final String dec = CipherUtil.decrypt(sid);
			String[] sp = dec.split("\\|");
			subscriber = subscriberService.find(Long.parseLong(sp[0]));
			company = companyService.find(Long.parseLong(sp[1]));
			String[] gr = sp[2].split(";");
			for (int i = 0; i < gr.length; i++) {
				if (gr[i].trim().length() > 0) {
					groups.add(groupService.find(Long.parseLong(gr[i])));
				}
			}
			// check if it also have products
			if (sp.length > 3) {
				String[] pr = sp[3].split(";");
				for (int i = 0; i < pr.length; i++) {
					if (pr[i].trim().length() > 0) {
						products.add(productService.find(Long.parseLong(pr[i])));
					}
				}
			}
			if (subscriber == null || company == null || groups.isEmpty()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private class RegistrationForm extends Form {

		private String email;
		private String name;
		private String pass;
		private String repass;

		public RegistrationForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new TextField("email").setRequired(true).setEnabled(false));
			add(new TextField("name").setRequired(true));
			add(new PasswordTextField("pass").setRequired(true));
			add(new PasswordTextField("repass").setRequired(true));
			add(new IFormValidator() {

				public FormComponent[] getDependentFormComponents() {
					return null;
				}

				public void validate(Form form) {
					if (subscriber.getId() == null) {
						String pass = UtilOld.toString(form.getRequest().getParameter("password"));
						if (!pass.equals(UtilOld.toString(form.getRequest().getParameter("repassword")))) {
							form.error(new String("The Password doesn't match the Retyped Password."));
						}
					}
				}

			});
		}

		@Override
		protected void onSubmit() {
			subscriber.setName(getName());
			subscriber.setPassword(Util.digest(getPass()));
			productService.updateProducts(subscriber, products);
			subscriberService.save(subscriber);
			info("Information saved with success!");
			setVisible(false);
			loginLink.setVisible(true);
		}

		public void setEmail(String username) {
			this.email = username;
		}

		public String getEmail() {
			return email;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}

		public String getPass() {
			return pass;
		}

		public void setRepass(String repass) {
			this.repass = repass;
		}

		public String getRepass() {
			return repass;
		}

	}

	private class AcceptInviteForm extends Form {

		private String compName;

		public AcceptInviteForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new Label("compName"));
			add(new Button("denyBt") {
				@Override
				public void onSubmit() {
					getRequestCycle().setRequestTarget(new RedirectRequestTarget("ui"));
				}
			});
		}

		@Override
		protected void onSubmit() {
			subscriberService.addToCompany(company, groups, subscriber);
			productService.updateProducts(subscriber, products);
			info("User joined the company with success");
			setVisible(false);
			loginLink.setVisible(true);
		}

		public void setCompName(String compName) {
			this.compName = compName;
		}

		public String getCompName() {
			return compName;
		}

	}

}
