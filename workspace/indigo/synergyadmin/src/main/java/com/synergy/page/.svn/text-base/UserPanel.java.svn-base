package com.synergy.page;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
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

public class UserPanel extends Panel {

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	ProductService productService;

	@SpringBean
	PurchaseService purchaseService;

	@SpringBean
	CompanyService clientService;
	final Label feedbackLabel = new Label("feedback", "");

	private RegisterUserPanel registerUserPanel;

	final Product vmail;

	final Product vchat;

	public UserPanel(String id) {
		super(id);
		vmail = this.productService.getProductByName(Product.VMAIL);
		vchat = this.productService.getProductByName(Product.VCHAT);
		registerUserPanel = createRegisterUserPanel();
		add(feedbackLabel);
		Company client = getClient();

		add(registerUserPanel.setVisible(false));

		this.setOutputMarkupId(true);

		add(new AjaxLink("addUserLink") {

			@Override
			public void onClick(AjaxRequestTarget arg0) {
				setResponsePage(ImportContactsMemberPage.class);
				// registerUserPanel.setVisible(true);
				// registerUserPanel.setUser(null);
				// arg0.addComponent(registerUserPanel);
				// arg0.addComponent(registerUserPanel.getParent());
			}

		});

		List<Subscriber> subscribers = subscriberService.subscribersFromClient(client);
		add(new UserListView("users", subscribers));
	}

	protected RegisterUserPanel createRegisterUserPanel() {
		return new RegisterUserPanel("addUserPanel", null);
	}

	private class UserListView extends ListView {

		public UserListView(String id, List<Subscriber> subscribers) {
			super(id, subscribers);
			setOutputMarkupId(true);
		}

		@Override
		protected void populateItem(ListItem item) {
			final Subscriber sub = (Subscriber) item.getModelObject();
			final String name = sub.getName() != null && sub.getName().length() > 0 ? sub.getName() : sub.getEmail();
			item.add(new Label("uname", name));
			StringBuffer sb = new StringBuffer();
			Collection<Group> groups = sub.getGroups();
			for (Group group : groups) {
				if (!group.getDeleted()) {
					sb.append(group.getName()).append("; ");
				}
			}
			if (sb.length() == 0) {
				sb.append("NULL; ");
			} else {
				sb.substring(0, sb.length() - 2);
			}
			item.add(new Label("gname", sb.toString().substring(0, sb.length() - 2)));

			// item.add(new Label("upublic", priv ? "Yes" : "No"));
			item.add(new AjaxLink("delUser") {

				@Override
				public void onClick(AjaxRequestTarget arg0) {
					// TODO Auto-generated method stub
					final UserPanel userPanel = UserPanel.this;
					subscriberService.delete(sub);
					// final PageParameters pageParameters = new
					// PageParameters();
					// pageParameters.put("wizardStep", "2");
					// setResponsePage(WelcomeMemberPage.class,
					// pageParameters);

					feedbackLabel.setModelObject("User deleted with success!");

					final UserListView userListView = UserListView.this;
					userListView.getList().remove(sub);
					// arg0.addComponent(userListView);
					arg0.addComponent(userPanel);
				}

			});

			item.add(new AjaxLink("editUser") {

				@Override
				public void onClick(AjaxRequestTarget arg0) {
					// setResponsePage(new EditUserMemberPage(sub));
					registerUserPanel.setVisible(true);
					registerUserPanel.setUser(sub);
					arg0.addComponent(registerUserPanel);
					arg0.addComponent(registerUserPanel.getParent());
				}
			});

			item.add(new AjaxLink("retrievePass") {

				@Override
				public void onClick(AjaxRequestTarget arg0) {
					// memberFeedback.setVisible(true);
					final UserPanel userPanel = UserPanel.this;
					try {
						if (subscriberService.emailPassword(sub.getEmail())) {
							//
							// info("The new password was sent to the email of the user.");
							feedbackLabel.setModelObject("Password reset with success and sent to the email of the user.");
						} else {
							feedbackLabel.setModelObject("Error: email not found for this user.");
						}
					} catch (Exception e) {
						e.printStackTrace();
						feedbackLabel.setModelObject("Error: the system couldn't reset the password.");
					}
					arg0.addComponent(userPanel);
					// setResponsePage(ContactsMemberPage.this);
					// setResponsePage(new EditUserMemberPage(sub));
				}
			});

			buildAccessPermissions(item, sub);

		}

	}

	protected void buildAccessPermissions(ListItem item, final Subscriber sub) {
		List<Product> subProducts = productService.getProducts(sub);
		final AjaxCheckModel vMailCheck = new AjaxCheckModel();
		vMailCheck.setObject(subProducts.contains(vmail));
		final UserPanel userPanel = UserPanel.this;
		item.add(new AjaxCheckBox("chkVMail", vMailCheck) {

			@Override
			protected void onUpdate(AjaxRequestTarget arg0) {
				feedbackLabel.setModelObject("");
				arg0.addComponent(userPanel);
				List<Product> cur = productService.getProducts(sub);
				if (Boolean.TRUE.equals(vMailCheck.obj)) {
					if (!checkCanAdd(sub, vMailCheck, vmail)) {
						return;
					}
					cur.add(vmail);
				} else {
					cur.remove(vmail);
				}
				productService.updateProducts(sub, cur);
			}

		});
		final AjaxCheckModel vChatCheck = new AjaxCheckModel();
		vChatCheck.setObject(subProducts.contains(vchat));
		item.add(new AjaxCheckBox("chkVChat", vChatCheck) {

			@Override
			protected void onUpdate(AjaxRequestTarget arg0) {
				feedbackLabel.setModelObject("");
				arg0.addComponent(userPanel);
				List<Product> cur = productService.getProducts(sub);
				if (Boolean.TRUE.equals(vChatCheck.obj)) {
					if (!checkCanAdd(sub, vChatCheck, vchat)) {
						return;
					}

					cur.add(vchat);
				} else {
					cur.remove(vchat);
				}
				productService.updateProducts(sub, cur);
			}

		});

	}

	private boolean checkCanAdd(final Subscriber sub, final AjaxCheckModel ajaxCheck, final Product product) {
		List<ProductInUse> puse = productService.getProductsInUse(sub.getCompany());
		for (ProductInUse cur : puse) {
			if (cur.product.equals(product)) {
				// if product reached the number of purchased, can't
				// add seat
				// TODO - optimize to receive by parameter the number of
				// purchased.
				if (cur.quantity >= purchaseService.getNumberOfPurchasedSeat(sub.getCompany(), product)) {
					ajaxCheck.setObject(Boolean.FALSE);
					feedbackLabel.setModelObject("You already is using all the purchased " + product.getName() + " seats. Buy more in Payment & Upgrade.");
					return false;
				}
				break;
			}
		}
		return true;
	}

	private Company getClient() {
		return ((QwicketSession) getSession()).getUser();
	}

	private static class AjaxCheckModel implements IModel {

		Object obj;

		public AjaxCheckModel() {

		}

		public Object getObject() {
			return obj;
		}

		public void setObject(Object arg0) {
			this.obj = arg0;
		}

		public void detach() {
			// TODO Auto-generated method stub

		}

	}

}
