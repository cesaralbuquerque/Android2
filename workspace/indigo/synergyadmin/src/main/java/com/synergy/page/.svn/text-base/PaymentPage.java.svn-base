package com.synergy.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.braintreegateway.ValidationError;
import com.braintreegateway.ValidationErrors;
import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.Purchase;
import com.synergy.service.BrainTreeService;
import com.synergy.service.CompanyService;
import com.synergy.service.ProductService;
import com.synergy.service.PurchaseService;
import com.synergy.service.SubscriberService;

public class PaymentPage extends MemberBasePage {

	@SpringBean
	ProductService productService;

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	PurchaseService purchaseService;

	@SpringBean
	CompanyService clientService;

	@SpringBean
	BrainTreeService brainTreeService;

	List<PaymentVO> payment;

	private Company company;

	private PageParameters pageParams;

	public PaymentPage() {
	}

	public PaymentPage(PageParameters params) {
		this.pageParams = params;
		this.company = clientService.find(((QwicketSession) getSession()).getUser().getId());

		// Add a FeedbackPanel for displaying our messages
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);

		// Add a form with an onSubmit implementation that sets a message
		Form frm = new PaymentForm();

		PaymentVO pmt = new PaymentVO();

		pmt.setAmount(params.getDouble("amt"));

		payment = new ArrayList<PaymentVO>();

		payment.add(pmt);

		add(new Label("amountLbl", "$" + pmt.getAmount().toString()));

		frm.add(new PaymentListView("payments", payment));

		add(frm);

		Customer customer = null;
		if (company.getBraintreeId() != null) {
			customer = brainTreeService.getCustomer(company.getBraintreeId());
			if (customer == null) {
				company.setBraintreeId(null);
				clientService.save(company);
			}
		}
		List<CardVO> cards = new ArrayList<CardVO>();
		if (customer != null) {
			final List<CreditCard> creditCards = customer.getCreditCards();
			for (CreditCard creditCard : creditCards) {
				cards.add(new CardVO(creditCard.getCardholderName(), creditCard.getMaskedNumber(), creditCard.getToken()));
			}
		}
		add(new CardListView(cards));
	}

	private class PaymentForm extends Form {

		public PaymentForm() {
			super("paymentForm");
		}

		protected void onSubmit() {
			QwicketSession session = (QwicketSession) Session.get();

			String info = null;

			PaymentVO pmt = null;

			if ((null != payment) && (payment.size() > 0)) {

				pmt = payment.get(0);

				if (null != session) {
					if (company.getBraintreeId() == null) {
						Result<Customer> newCustomer = brainTreeService.createCustomerWithNameAndEmail(company.getCompanyName(), company.getEmail());
						if (newCustomer.isSuccess()) {
							company.setBraintreeId(newCustomer.getTarget().getId());
							clientService.save(company);
						} else {
							showErrors(newCustomer);
							return;
						}
					}
					Result<Customer> newCustomer = brainTreeService.addCreditCard(company.getBraintreeId(), pmt.getCardHolderName(), pmt.getCreditCardNo(), pmt.getCardExpiryDate(), pmt.getCvv());
					if (!newCustomer.isSuccess()) {
						showErrors(newCustomer);
						return;
					} else {
						String paymentToken = newCustomer.getTarget().getCreditCards().get(0).getToken();
						if (subscribePayment(paymentToken)) {
							setResponsePage(CurrentPlan.class);
						}
					}
				}
			}
		}
	}

	private class CardsForm extends Form {

		private CardVO cardVo;

		public CardsForm(CardVO cardVO) {
			super("cardForm");
			this.cardVo = cardVO;
			add(new Label("nameLbl", cardVo.cardHolderName));
			add(new Label("maskedNumberLbl", cardVo.maskNumber));
			this.cardVo = cardVO;
		}

		protected void onSubmit() {
			QwicketSession session = (QwicketSession) Session.get();

			String info = null;

			PaymentVO pmt = null;

			if ((null != payment) && (payment.size() > 0)) {

				pmt = payment.get(0);

				if (null != session) {
					// String paymentToken =
					// newCustomer.getTarget().getCreditCards().get(0).getToken();
					if (subscribePayment(cardVo.paymentToken)) {
						setResponsePage(CurrentPlan.class);
					}
				}
			}
		}
	}

	private boolean subscribePayment(String paymentToken) {
		String vChatPlan = pageParams.getKey("VChat");

		String vMailPlan = pageParams.getKey("VMail");

		if ((null != vChatPlan) && (vChatPlan.trim().length() > 0)) {
			final int qVChat = pageParams.getInt("VChat");
			vChatPlan = "VCHAT_PLAN_" + qVChat;
			float total = 9.95f * qVChat;
			Product product = productService.getProductByName(Product.VCHAT);
			final Result<Subscription> subscribe = brainTreeService.subscribe(paymentToken, vChatPlan);
			if (!doPurchase(qVChat, total, product, subscribe)) {
				return false;
			}
		}

		if ((null != vMailPlan) && (vMailPlan.trim().length() > 0)) {

			final int qVMail = pageParams.getInt("VMail");
			vMailPlan = "VMAIL_PLAN_" + qVMail;
			float total = 4.95f * qVMail;
			Product product = productService.getProductByName(Product.VMAIL);
			final Result<Subscription> subscribe = brainTreeService.subscribe(paymentToken, vMailPlan);
			if (!doPurchase(qVMail, total, product, subscribe)) {
				return false;
			}
		}
		return true;
	}

	private boolean doPurchase(int q, float total, Product product, final Result<Subscription> subscribe) {
		if (subscribe.isSuccess()) {
			List<Purchase> oldPurchase = purchaseService.getPurchaseByProduct(company, product);
			purchaseService.purchaseProduct(company, product, q, total, q + " Users / USD " + total, subscribe.getTarget().getId());
			// cancel the old purchase for this product.
			for (Purchase purchase : oldPurchase) {
				if (purchase.getSubscriptionId() != null) {
					final Result<Subscription> cancelSubscribe = brainTreeService.cancelSubscribe(purchase.getSubscriptionId());
					if (!cancelSubscribe.isSuccess()) {
						showErrors(cancelSubscribe);
						continue;
					}
					purchaseService.cancelPurchase(purchase);
				}
			}
			info(product.getName() + " puchased with success");
			return true;
		} else {
			error(product.getName() + " error purchasing: ");
			showErrors(subscribe);
			return false;
		}
	}

	private void showErrors(Result<?> result) {
		error(result.getMessage());
		final ValidationErrors errors = result.getErrors();
		for (ValidationError e : errors.getAllDeepValidationErrors()) {
			error(e.getCode().name() + " - " + e.getMessage());
		}
	}

	private class CardListView extends ListView {

		public CardListView(List<CardVO> list) {
			super("cardList", list);
		}

		@Override
		protected void populateItem(ListItem item) {
			item.add(new CardsForm((CardVO) item.getModelObject()));
		}

	}

	private class PaymentListView extends ListView {

		public PaymentListView(String id, List<PaymentVO> payment) {
			super(id, payment);
			setOutputMarkupId(true);
		}

		@Override
		protected void populateItem(ListItem item) {
			final PaymentVO pmt = (PaymentVO) item.getModelObject();

			TextField cardHolderNameTF = new TextField("cardHolderName", new PropertyModel(pmt, "cardHolderName"));

			TextField creditCardNoTF = new TextField("creditCardNo", new PropertyModel(pmt, "creditCardNo"));

			TextField cardExpiryDateTF = new TextField("cardExpiryDate", new PropertyModel(pmt, "cardExpiryDate"));

			TextField cvvTF = new TextField("cvv", new PropertyModel(pmt, "cvv"));

			creditCardNoTF.setRequired(true);
			cardExpiryDateTF.setRequired(true);

			item.add(creditCardNoTF);
			item.add(cardExpiryDateTF);
			item.add(cvvTF);
			item.add(cardHolderNameTF);
		}
	}

	private class CardVO {

		String cardHolderName;

		String maskNumber;

		String paymentToken;

		public CardVO(String cardholderName2, String maskedNumber, String paymentToken) {
			this.cardHolderName = cardholderName2;
			this.maskNumber = maskedNumber;
			this.paymentToken = paymentToken;
		}

	}
}
