package com.synergy.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Group;
import com.synergy.model.Subscriber;
import com.synergy.service.GroupService;
import com.synergy.service.SubscriberService;
import com.synergy.util.Util;
import com.synergy.util.UtilOld;

public class EditUserMemberPage extends MemberBasePage {

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	GroupService groupService;

	public EditUserMemberPage() {
		this(null);
	}

	public EditUserMemberPage(Subscriber sub) {
		sub = subscriberService.find(sub.getId());
		add(new BookmarkablePageLink("btCancel", ContactsMemberPage.class));
		UserForm form = new UserForm("edtProfForm", sub);
		add(form);
		if (!form.isVisible()) {
			error("It's necessary to have at least one group to be able to add user.");
		}
	}

	private class UserForm extends Form {

		private String name;

		private String email;

		private Collection<Group> group;

		private Subscriber subscriber = new Subscriber();

		List<Group> groups = groupService
				.groupsFromCompany(((QwicketSession) getSession()).getUser());

		private List<GroupWrapper> groupWrappers = new ArrayList<GroupWrapper>();

		private Boolean priv = false;

		private String oldpassword;
		private String password;
		private String repassword;

		public UserForm(String id, Subscriber sub) {
			super(id);
			add(new FeedbackPanel("profFeedback"));
			setModel(new CompoundPropertyModel(this));
			add(new TextField("name").setRequired(true));
			add(new TextField("email").setRequired(true));
			final PasswordTextField oldpass = new PasswordTextField(
					"oldpassword");
			final PasswordTextField pass = new PasswordTextField("password");
			final PasswordTextField repass = new PasswordTextField("repassword");
			add(oldpass.setRequired(false));
			add(pass.setRequired(false));
			add(repass.setRequired(false));

			if (sub != null) {
				this.subscriber = subscriberService.find(sub.getId());
				this.name = subscriber.getName();
				this.email = subscriber.getEmail();
			}
			for (Group group : groups) {
				groupWrappers.add(new GroupWrapper(group.getId(), group
						.getName(), subscriber.getGroups().contains(group)));
			}

			ListView lv = new ListView("groups", groupWrappers) {

				@Override
				protected void populateItem(ListItem item) {
					GroupWrapper gw = (GroupWrapper) item.getModelObject();
					item.add(new Label("gname", gw.getName()));
					item.add(new CheckBox("gcheck", new PropertyModel(gw,
							"selected")));

				}

			};
			lv.setReuseItems(true);
			add(lv);

			add(new IFormValidator() {

				public FormComponent[] getDependentFormComponents() {
					// TODO Auto-generated method stub
					return null;
				}

				public void validate(Form form) {
					String email = UtilOld.toString(form.getRequest()
							.getParameter("email"));
					if (email.indexOf("@") == -1) {
						form.error(new String("Invalid email."));
					}

					String oldpass = UtilOld.toString(form.getRequest()
							.getParameter("oldpassword"));
					if (oldpass != null && oldpass.trim().length() > 0) {
						String pass = UtilOld.toString(form.getRequest()
								.getParameter("password"));
						if (pass != null && pass.trim().length() > 0) {
							form.error("The Password can't be empty.");
							return;
						}

						if (!Arrays.equals(subscriber.getPassword(), Util
								.digest(oldpass))) {
							form
									.error(new String(
											"The Old Password doesn't match the current password."));
							return;
						}
						if (!pass.equals(UtilOld.toString(form.getRequest()
								.getParameter("repassword")))) {
							form
									.error(new String(
											"The Password doesn't match the Retyped Password."));
						}
					}
				}

			});
		}

		@Override
		protected void onSubmit() {
			if (!this.hasError()) {
				subscriber = subscriberService.find(subscriber.getId());
				subscriber.setName(name);
				subscriber.setEmail(email);
				subscriber.getGroups().clear();
				for (GroupWrapper group : groupWrappers) {
					if (group.getSelected()) {
						subscriber.getGroups().add(
								groupService.find(group.getId()));
					}
				}
				if (subscriber.getGroups().isEmpty()) {
					this
							.error("The user need to be associated with at least one group.");
					return;
				}
				if (password != null) {
					subscriber.setPassword(Util.digest(password));
				}
				subscriberService.save(subscriber);
				ContactsMemberPage contactsMemberPage = new ContactsMemberPage();
				contactsMemberPage.memberFeedback.setVisible(true);
				contactsMemberPage.memberFeedback
						.info("User saved with success.");
				setResponsePage(contactsMemberPage);
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

		public void setPriv(Boolean priv) {
			this.priv = priv;
		}

		public Boolean getPriv() {
			return priv;
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

		public void setOldpassword(String oldPassword) {
			this.oldpassword = oldPassword;
		}

		public String getOldpassword() {
			return oldpassword;
		}

	}

	private class PassForm extends Form {

		private Subscriber subscriber = null;

		public PassForm(String id, Subscriber sub) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new FeedbackPanel("passFeedback"));
			this.subscriber = sub;

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
				subscriberService.save(subscriber);
				setResponsePage(ContactsMemberPage.class);
			}
		}

	}

}
