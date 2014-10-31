package com.synergy.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Request;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.synergy.service.BrainTreeService;
import com.synergy.service.ProductService;

public class PaymentSuccessPage extends BasePageMemberAdminLogin {

	@SpringBean
	ProductService productService;

	@SpringBean
	BrainTreeService brainTreeService;

	public PaymentSuccessPage(String title) {
		super(title);
	}

	public PaymentSuccessPage() {
		this("Payment Confirm");

		// Add a FeedbackPanel for displaying our messages
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);

		try {
			String url = this.getRequestUrl();

			int idx = url.indexOf("?");

			String queryString = url.substring(idx + 1);

			Result<Transaction> result = brainTreeService.purchase(queryString);

			Label amountLbl = null;
			Label transIdLbl = null;
			Label creditCaredNoLbl = null;
			Label cardTypeLbl = null;

			Label errorLbl = new Label("errorLbl", "Transaction successful.");

			if (result.isSuccess()) {
				Transaction transaction = result.getTarget();

				String str = "1";

				amountLbl = new Label("amountLbl", transaction.getAmount()
						.toString());
				transIdLbl = new Label("transIdLbl", transaction.getId());
				creditCaredNoLbl = new Label("creditCardNoLbl", transaction
						.getCreditCard().getMaskedNumber());
				cardTypeLbl = new Label("cardTypeLbl", transaction
						.getCreditCard().getCardType());
				//

			} else {

				errorLbl = new Label("errorLbl", "Error occurred.");

				// gateway,
				// result.getParameters(),
				// result.getErrors()
			}

			add(amountLbl);
			add(transIdLbl);
			add(creditCaredNoLbl);
			add(cardTypeLbl);
			add(errorLbl);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getRequestUrl() {
		// This is a wicket-specific request interface
		final Request request = getRequest();

		if (request instanceof WebRequest) {
			final WebRequest wr = (WebRequest) request;
			// But this is the real thing
			final HttpServletRequest hsr = wr.getHttpServletRequest();
			String reqUrl = hsr.getRequestURL().toString();
			final String queryString = hsr.getQueryString();

			if (queryString != null) {
				reqUrl += "?" + queryString;
			}
			return reqUrl;
		}
		return null;
	}
}
