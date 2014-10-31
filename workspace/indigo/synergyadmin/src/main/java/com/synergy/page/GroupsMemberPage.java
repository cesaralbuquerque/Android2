package com.synergy.page;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.service.GroupService;

public class GroupsMemberPage extends MemberBasePage {

	@SpringBean
	GroupService groupService;

	public GroupsMemberPage() {
		add(new BookmarkablePageLink("contactLink", ContactsMemberPage.class));
		add(new MemberGroupsPanel("groupPanel"));

	}

	private Company getClient() {
		return ((QwicketSession) getSession()).getUser();
	}

}
