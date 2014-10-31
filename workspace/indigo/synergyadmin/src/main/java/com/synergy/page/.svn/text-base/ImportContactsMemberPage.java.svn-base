package com.synergy.page;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
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
import com.synergy.vo.ProductInUse;

public class ImportContactsMemberPage extends MemberBasePage {

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

	public ImportContactsMemberPage() {
		super();
		add(new BookmarkablePageLink("groupsLink", GroupsMemberPage.class));
		add(feedbackPanel);
		add(new UserForm("form"));
		okLink = new AjaxLink("btOk") {

			@Override
			public void onClick(AjaxRequestTarget arg0) {
				setResponsePage(ContactsMemberPage.class);
			}

		};
		add(okLink);
		okLink.setVisible(false);
	}

	private class UserForm extends Form {

		private String email;

		private Group group;

		List<Group> groups = groupService.groupsFromCompany(((QwicketSession) getSession()).getUser());
		List<Product> products = productService.getAll();

		private List<GroupWrapper> groupWrappers = new ArrayList<GroupWrapper>();
		private List<GroupWrapper> prodWrappers = new ArrayList<GroupWrapper>();

		InternetAddress[] emailAddrs;

		public UserForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			final TextArea txtEmail = new TextArea("email");
			add(txtEmail.setRequired(true));
			for (Group group : groups) {
				groupWrappers.add(new GroupWrapper(group.getId(), group.getName(), false));
			}
			final MultipleSelectDropDown selectGroups = new MultipleSelectDropDown("selectGroups", groupWrappers);
			selectGroups.setOutputMarkupId(true);
			add(selectGroups);

			for (Product prod : products) {
				prodWrappers.add(new GroupWrapper(prod.getId(), prod.getName(), true));
			}
			final MultipleSelectDropDown selectProducts = new MultipleSelectDropDown("selectProducts", prodWrappers);
			selectProducts.setOutputMarkupId(true);
			add(selectProducts);
			txtEmail.add(new AjaxFormComponentUpdatingBehavior("onblur") {

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
				}

			});

			add(new IFormValidator() {

				public FormComponent[] getDependentFormComponents() {
					return null;
				}

				public void validate(Form form) {
					String email = form.getRequest().getParameter("email");
					email = email.replaceAll(";", ",");
					try {
						emailAddrs = InternetAddress.parse(email);
					} catch (AddressException e) {
						error("Invalid email format: " + e.getMessage());
					}
				}

			});

		}

		@Override
		protected void onSubmit() {
			if (!this.hasError()) {
				setVisible(false);
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
				final int totalEmails = emailAddrs.length;
				boolean prodOk = true;
				ArrayList<Product> pSel = new ArrayList<Product>();
				for (GroupWrapper p : prodWrappers) {
					if (p.getSelected()) {
						final Product product = productService.find(p.getId());
						boolean cur = checkCanAdd(company, product, totalEmails);
						if (!cur) {
							prodOk = false;
						}
						pSel.add(product);
					}
				}

				if (!prodOk) {
					return;
				}
				setResponsePage(new ProcessImportContactsMemberPage(emailAddrs, company, gSel, pSel));
				/*
				 * StringBuffer errorEmail = new StringBuffer(); for (int i = 0;
				 * i < totalEmails; i++) { InternetAddress ia = emailAddrs[i];
				 * info("Please wait..."); info("Processing email " + i +
				 * " out of " + totalEmails + ": " + ia.getAddress());
				 * Subscriber subscriber =
				 * subscriberService.getByEmail(ia.getAddress()); if (subscriber
				 * == null || subscriber.getCompany() == null) { // if
				 * subscriber doesn't exist with this email, then // create a
				 * new one subscriber = subscriberService.create(company, gSel,
				 * ia.getPersonal(), ia.getAddress(), ""); } try {
				 * subscriberService.emailJoinCompanyInvite(subscriber, company,
				 * gSel, pSel); } catch (RuntimeException e) {
				 * errorEmail.append(ia.toString()).append(", "); } } if
				 * (errorEmail.length() > 0) {error(
				 * "The system couldn't send an email to the following emails: "
				 * + errorEmail);
				 * error("Please review the emails, you can go back and resend it."
				 * ); } else { okLink.setVisible(true); }
				 */

			}
		}

		private boolean checkCanAdd(Company company, final Product product, int qtde) {
			// /TODO - optmize this code
			List<ProductInUse> puse = productService.getProductsInUse(company);
			for (ProductInUse cur : puse) {
				if (cur.product.equals(product)) {
					// if product reached the number of purchased, can't
					// add seat

					final Integer purchased = purchaseService.getNumberOfPurchasedSeat(company, product);
					int available = purchased - cur.quantity;
					if (qtde > available) {
						error("You are setting " + qtde + " seats for the product " + product.getName() + ", but you currently have " + available + " seats available. Buy more in Payment & Upgrade.");
						return false;
					}
					break;
				}
			}
			return true;
		}

		@Override
		public boolean isVisible() {
			return !groups.isEmpty();
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}
	}

	private Company getClient() {
		return ((QwicketSession) getSession()).getUser();
	}

}
