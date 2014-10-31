package com.synergy.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.model.Subscriber;
import com.synergy.service.CompanyService;
import com.synergy.service.GroupService;
import com.synergy.service.SubscriberService;
import com.synergy.util.Util;
import com.synergy.util.UtilOld;

public class RegisterUserPanel extends Panel {

	@SpringBean
	GroupService groupService;

	@SpringBean
	CompanyService companyService;

	@SpringBean
	SubscriberService subscriberService;

	UserForm form = new UserForm("form");
	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");

	private FormRedirect formRedirect;

	private boolean needBuyCon;

	public RegisterUserPanel(String id, FormRedirect formRedirect) {
		super(id);
		this.formRedirect = formRedirect;
		setOutputMarkupId(true);
		add(feedbackPanel);
		add(form);
	}

	public void setUser(Subscriber user) {
		if (user == null && needBuyCon) {
			// can't add user, if needs to buy connection.
			form.setVisible(false);
			return;
		}
		remove(form);
		add(form = new UserForm("form"));
		if (user != null) {
			form.setEmail(user.getEmail());
			form.setName(user.getName());
		}
		form.setVisible(true);
	}

	private Company getClient() {
		return ((QwicketSession) getSession()).getUser();
	}

	private class UserForm extends Form {

		private String name;

		private String email;

		private String password;

		private String repassword;

		private Group group;

		private Subscriber subscriber = new Subscriber();

		List<Group> groups = groupService.groupsFromCompany(((QwicketSession) getSession()).getUser());

		private List<GroupWrapper> groupWrappers = new ArrayList<GroupWrapper>();

		private Boolean priv = false;

		public UserForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			final TextField txtName = new TextField("name");
			add(txtName.setRequired(true));
			final TextField txtEmail = new TextField("email");
			add(txtEmail.setRequired(true));
			final PasswordTextField pass = new PasswordTextField("password");
			final PasswordTextField repass = new PasswordTextField("repassword");
			add(pass.setRequired(true));
			add(repass.setRequired(true));
			for (Group group : groups) {
				groupWrappers.add(new GroupWrapper(group.getId(), group.getName(), false));
			}
			final MultipleSelectDropDown selectGroups = new MultipleSelectDropDown("selectGroups", groupWrappers);
			selectGroups.setOutputMarkupId(true);
			add(selectGroups.setVisible(false));

			ImageButton selGroupLink = new ImageButton("selGroupsLink");
			selGroupLink.setDefaultFormProcessing(false);

			add(selGroupLink.setVisible(true));
			txtName.add(new AjaxFormComponentUpdatingBehavior("onblur") {

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
				}

			});
			txtEmail.add(new AjaxFormComponentUpdatingBehavior("onblur") {

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
				}

			});
			pass.add(new AjaxFormComponentUpdatingBehavior("onfocus") {

				@Override
				protected void onUpdate(AjaxRequestTarget ajax) {
					// if (selectGroups.isVisible()) {
					remove(selectGroups);
					add(selectGroups);
					selectGroups.setVisible(false);
					ajax.addComponent(RegisterUserPanel.this);
					// }
				}

			});
			repass.add(new AjaxFormComponentUpdatingBehavior("onfocus") {

				@Override
				protected void onUpdate(AjaxRequestTarget ajax) {
					// if (selectGroups.isVisible()) {
					remove(selectGroups);
					add(selectGroups);
					selectGroups.setVisible(false);
					ajax.addComponent(RegisterUserPanel.this);
					// }
				}

			});

			// selGroupLink.add(new AjaxFormComponentUpdatingBehavior("onfocus")
			// {
			//
			// @Override
			// protected void onUpdate(AjaxRequestTarget ajax) {
			// if (!selectGroups.isVisible()) {
			// remove(selectGroups);
			// add(selectGroups);
			selectGroups.setVisible(true);
			// ajax.addComponent(RegisterUserPanel.this);
			// }
			// }
			//
			// });

			add(new AjaxLink("btCancel") {

				@Override
				public void onClick(AjaxRequestTarget arg0) {
					RegisterUserPanel.this.setVisible(false);
					arg0.addComponent(RegisterUserPanel.this);
				}

			});

			add(new IFormValidator() {

				public FormComponent[] getDependentFormComponents() {
					return null;
				}

				public void validate(Form form) {
					String email = UtilOld.toString(form.getRequest().getParameter("email"));
					if (email.indexOf("@") == -1) {
						form.error(new String("Invalid email."));
					}
					if(subscriberService.emailInUse(email)){
						form.error(new String("This email already is linked to another account."));
					}
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
			if (!this.hasError()) {
				ArrayList<Group> gSel = new ArrayList<Group>();
				for (GroupWrapper group : groupWrappers) {
					if (group.getSelected()) {
						gSel.add(groupService.find(group.getId()));
					}
				}
				if (gSel.isEmpty()) {
					this.error("The user need to be associated with at least one group.");
					return;
				}
				Company company = companyService.find(((QwicketSession) getSession()).getUser().getId());
				Subscriber subscriber = subscriberService.create(company, gSel, name, email, password);
//				try {
//					subscriberService.emailUserCreated(subscriber, password);
//				} catch (RuntimeException e) {
//					// TODO - Fucking work around, the service call must throw a
//					// SynergyChatException.
//					if (e.getMessage() != null && e.getMessage().indexOf("mail.synergychat.com") > -1) {
//						// Ignore this exception here
//						return;
//					}
//					throw e;
//				}
//				ContactsMemberPage contactsMemberPage = new ContactsMemberPage();
//				contactsMemberPage.memberFeedback.setVisible(true);
//				contactsMemberPage.memberFeedback.info("User saved with success.");
//				setResponsePage(contactsMemberPage);
		
				formRedirect.redirect(null);

			}
		}

		@Override
		public boolean isVisible() {
			return !groups.isEmpty();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPassword() {
			return password;
		}

		public void setRepassword(String repassword) {
			this.repassword = repassword;
		}

		public String getRepassword() {
			return repassword;
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}

		public void setPriv(Boolean priv) {
			this.priv = priv;
		}

		public Boolean getPriv() {
			return priv;
		}
	}

}
