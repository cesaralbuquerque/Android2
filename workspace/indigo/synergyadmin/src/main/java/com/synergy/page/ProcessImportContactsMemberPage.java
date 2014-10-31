package com.synergy.page;

import java.util.Collection;

import javax.mail.internet.InternetAddress;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;
import com.synergy.service.CompanyService;
import com.synergy.service.ProductService;
import com.synergy.service.PurchaseService;
import com.synergy.service.SubscriberService;

public class ProcessImportContactsMemberPage extends MemberBasePage {

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	CompanyService companyService;

	@SpringBean
	ProductService productService;

	@SpringBean
	PurchaseService purchaseService;

	private AjaxLink okLink;

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");

	private InternetAddress[] emailAddrs;

	private Company company;

	private Collection<Group> gSel;

	private Collection<Product> pSel;

	public ProcessImportContactsMemberPage(InternetAddress[] emailAddrs, Company company, Collection<Group> gSel, Collection<Product> pSel) {
		this.emailAddrs = emailAddrs;
		this.company = company;
		this.gSel = gSel;
		this.pSel = pSel;
		add(new BookmarkablePageLink("groupsLink", GroupsMemberPage.class));
		add(feedbackPanel);
		okLink = new AjaxLink("btOk") {

			@Override
			public void onClick(AjaxRequestTarget arg0) {
				setResponsePage(ContactsMemberPage.class);
			}

		};
		add(okLink);
		add(new ProcessForm());
		okLink.setVisible(false);
		int s = emailAddrs.length * 10;
		info("You are about to import " + emailAddrs.length + " user(s). This will take around " + s + " seconds");
		info("Click the Import button to start the proccess");
	}

	private class ProcessForm extends Form {

		public ProcessForm() {
			super("form");
		}

		@Override
		protected void onSubmit() {
			int totalEmails = emailAddrs.length;
			StringBuffer errorEmail = new StringBuffer();
			for (int i = 0; i < totalEmails; i++) {
				InternetAddress ia = emailAddrs[i];
				Subscriber subscriber = subscriberService.getByEmail(ia.getAddress());
				if (subscriber == null || subscriber.getCompany() == null) {
					// if subscriber doesn't exist with this email, then
					// create a new one
					subscriber = subscriberService.create(company, gSel, ia.getPersonal(), ia.getAddress(), "");
				}
				try {
					subscriberService.emailJoinCompanyInvite(subscriber, company, gSel, pSel);
				} catch (RuntimeException e) {
					errorEmail.append(ia.toString()).append(", ");
				}
			}
			if (errorEmail.length() > 0) {
				error("The system couldn't send an email to the following emails: " + errorEmail);
				error("Please review the emails.");
			} else {
				info("Process finished");
				okLink.setVisible(true);
			}
		}
	}

	private Company getClient() {
		return ((QwicketSession) getSession()).getUser();
	}

}
