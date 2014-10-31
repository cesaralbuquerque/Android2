package com.synergy.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.service.CompanyService;
import com.synergy.service.SubscriberService;

public class WelcomeMemberPage extends MemberBasePage {

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	CompanyService clientService;

	Panel wizardPanel;

	public WelcomeMemberPage() {
		this(null);
	}

	public WelcomeMemberPage(PageParameters parameters) {
		super();
		add(new BookmarkablePageLink("profileLink2", ProfileMemberPage.class));
		add(new BookmarkablePageLink("paymentLink2", CurrentPlan.class));
		// add(new BookmarkablePageLink("contactsLink2",
		// ContactsMemberPage.class));
		// add(new BookmarkablePageLink("appearanceLink2",
		// AppearanceMemberPage.class));
		// add(new BookmarkablePageLink("faqLink", FAQS.class));

		final Company user = ((QwicketSession) getSession()).getUser();
		if (user == null) {
			return;
		}
		final String username = user == null ? "" : user.getCompanyName();
		add(new Label("unameProfile", username));
		final PageParameters pageParameters = new PageParameters();
		pageParameters.put("wizardStep", "1");
		// add(new BookmarkablePageLink("startWizardLink",
		// WelcomeMemberPage.class, pageParameters));

		// wizardPanel.setVisible(users.isEmpty());
	}

	private Company getClient() {
		return clientService.find(((QwicketSession) getSession()).getUser().getId());
	}

}
