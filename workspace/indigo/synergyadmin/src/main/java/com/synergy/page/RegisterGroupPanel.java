package com.synergy.page;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.service.GroupService;
import com.synergy.util.UtilOld;

public class RegisterGroupPanel extends Panel {

	@SpringBean
	GroupService groupService;
	GroupForm form = new GroupForm("form", null);
	private FormRedirect formRedirect;

	public RegisterGroupPanel(String id, FormRedirect formRedirect) {
		super(id);
		this.formRedirect = formRedirect;
		setOutputMarkupId(true);
		add(new FeedbackPanel("feedback"));

		add(form);

	}

	public void setGroup(Group group) {
		remove(form);
		add(form = new GroupForm("form", group));
	}

	private Company getClient() {
		return ((QwicketSession) getSession()).getUser();
	}

	private class GroupForm extends Form {

		private String name;

		private String email;

		private Group group = new Group();

		private Byte privacy = 0;

		public GroupForm(String id, Group group) {
			this(id);
			if (group != null) {
				this.group = groupService.find(group.getId());
				this.name = group.getName();
				this.email = group.getEmail();
				this.privacy = group.getPrivacy();
			} else {
				this.name = "Support, Sales,...";
				String companyName = getClient().getCompanyName();
				if (companyName != null && companyName.trim().length() > 0) {
					companyName = companyName.replaceAll(" ", "");
					this.email = "support@" + companyName + ".com";
				} else {
					this.email = "Group@CompanyName.com";
				}
			}
		}

		public GroupForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new AjaxLink("btCancel") {

				@Override
				public void onClick(AjaxRequestTarget arg0) {
					RegisterGroupPanel.this.setVisible(false);
					arg0.addComponent(RegisterGroupPanel.this);
				}

			});
			add(new TextField("name").setRequired(true));
			add(new TextField("email").setRequired(true));
			DropDownChoice ddc = new DropDownChoice("privacy");
			add(ddc);
			ArrayList<Byte> opts = new ArrayList<Byte>();
			opts.add(Group.PRIVATE);
			opts.add(Group.PUBLIC);
			opts.add(Group.INDIVIDUAL);
			ddc.setChoices(opts);
			ddc.setChoiceRenderer(new IChoiceRenderer() {

				public Object getDisplayValue(Object object) {
					return Group.PrivacyToString((Byte) object);
				}

				public String getIdValue(Object object, int index) {
					return object.toString();
				}

			});
			add(new IFormValidator() {

				public FormComponent[] getDependentFormComponents() {
					// TODO Auto-generated method stub
					return null;
				}

				public void validate(Form form) {
					String email = UtilOld.toString(form.getRequest().getParameter("email"));
					if (email.indexOf("@") == -1) {
						form.error(new String("Invalid email."));
					}
				}

			});
		}

		@Override
		protected void onSubmit() {
			if (!this.hasError()) {
				group.setName(name);
				group.setCompany(getClient());
				group.setPrivacy(privacy);
				group.setEmail(email);
				groupService.save(group);

				// final PageParameters pageParameters = new PageParameters();
				// pageParameters.put("wizardStep", "1");
				// setResponsePage(WelcomeMemberPage.class, pageParameters);
				formRedirect.redirect(null);
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Byte getPrivacy() {
			return privacy;
		}

		public void setPrivacy(Byte privacy) {
			this.privacy = privacy;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getEmail() {
			return email;
		}
	}

}
