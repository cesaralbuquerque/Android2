package com.synergy.page;

import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.service.GroupService;

public class GroupsPanel extends Panel {

	@SpringBean
	GroupService groupService;

	final RegisterGroupPanel registerGroupPanel;

	final Label feedbackLabel = new Label("feedback", "");

	public GroupsPanel(String id) {
		super(id);
		registerGroupPanel = createRegisterGroupPanel();
		add(feedbackLabel);

		add(feedbackLabel);

		add(registerGroupPanel.setVisible(false));

		this.setOutputMarkupId(true);

		add(new AjaxLink("addGroupLink") {

			@Override
			public void onClick(AjaxRequestTarget arg0) {
				registerGroupPanel.setVisible(true);
				registerGroupPanel.setGroup(null);
				arg0.addComponent(registerGroupPanel);
				arg0.addComponent(registerGroupPanel.getParent());
			}

		});

		Company client = ((QwicketSession) getSession()).getUser();

		List<Group> groups = groupService.groupsFromCompany(client);
		add(new GroupListView("groups", groups));
	}

	protected RegisterGroupPanel createRegisterGroupPanel() {
		return new RegisterGroupPanel("addGroupPanel", new FormRedirect() {
			
			public void redirect(Object param) {
				final PageParameters pageParameters = new PageParameters();
				pageParameters.put("wizardStep", "1");
				setResponsePage(WelcomeMemberPage.class, pageParameters);
			}

		});
	}

	private class GroupListView extends ListView {

		public GroupListView(String id, List<Group> groups) {
			super(id, groups);
		}

		@Override
		protected void populateItem(ListItem item) {
			final Group group = (Group) item.getModelObject();
			item.add(new Label("gname", group.getName()));
			String email = group.getEmail();
			if (email == null || email.length() == 0) {
				email = "-";
			}
			item.add(new Label("gemail", email));
			String priv = Group.PrivacyToString(group.getPrivacy());
			item.add(new Label("gpublic", priv));
			item.add(new AjaxLink("delGroup") {

				@Override
				public void onClick(AjaxRequestTarget arg0) {
					groupService.delete(group);

					feedbackLabel.setModelObject("Group deleted with success!");
					final GroupListView userListView = GroupListView.this;
					userListView.getList().remove(group);
					// arg0.addComponent(userListView);
					arg0.addComponent(GroupsPanel.this);
				}

			});

			item.add(new AjaxLink("editGroup") {

				@Override
				public void onClick(AjaxRequestTarget arg0) {
					// TODO Auto-generated method stub
					registerGroupPanel.setVisible(true);
					registerGroupPanel.setGroup(group);
					arg0.addComponent(registerGroupPanel);
					arg0.addComponent(registerGroupPanel.getParent());
				}
			});

		}

	}

	private Company getClient() {
		return ((QwicketSession) getSession()).getUser();
	}

}
