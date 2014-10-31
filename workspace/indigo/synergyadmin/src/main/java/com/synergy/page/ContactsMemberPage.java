package com.synergy.page;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.service.CompanyService;
import com.synergy.service.SubscriberService;

public class ContactsMemberPage extends MemberBasePage {

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	CompanyService clientService;

	public ContactsMemberPage() {
		super();

		add(new BookmarkablePageLink("groupsLink", GroupsMemberPage.class));
		add(new MemberUserPanel("userPanel"));

		// add(buyLink);
		//
		// add(pageLink);
		// add(new Label("qtdUsers", Integer.toString(totSub)));
		// add(new Label("totUsers", Integer.toString(totConn)));
		//
		// add(new Label("clientLbl", client.getCompanyName()));

	}

	private Company getClient() {
		return ((QwicketSession) getSession()).getUser();
	}

}
