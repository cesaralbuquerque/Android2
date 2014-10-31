package com.synergy.page;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.synergy.app.QwicketSession;
import com.synergy.service.BrainTreeService;
import com.synergy.service.ProductService;

public class PaymentConfirmPage extends BasePageMemberAdminLogin {

//	private static final short CUSTOMER_NAME = 0;
	
	
	@SpringBean
	ProductService productService;

	@SpringBean
	BrainTreeService brainTreeService;

	public PaymentConfirmPage(String title) {
		super(title);
	}

	public PaymentConfirmPage() {
		this("Payment Confirm");

		try {
			String url = this.getRequestUrl();
			
			int idx = url.indexOf("?");
			
			String queryString = url.substring(idx+1);
			
			Result<Transaction> result = brainTreeService.purchase(queryString);
			
			Label amountLbl = null;
			Label transIdLbl = null;
			Label creditCardNoLbl = null;
			Label cardTypeLbl = null;

			QwicketSession session = (QwicketSession) Session.get();
			
			String info = null;
			
			if (null != session) {
				info = session.findAttribute("info").toString();
				
				if(null != info) {
					
				}
				
				String[] infos = info.split("|");
				
				for (String infoStr : infos) {
					info += (" val: "+infoStr);
				}
			}
			
			Label errorLbl = new Label("errorLbl", "Transaction successful. Info: " + info);
			
			if (result.isSuccess()) {
				Transaction transaction = result.getTarget();

				amountLbl = new Label("amountLbl", transaction.getAmount().toString());
				transIdLbl = new Label("transIdLbl", transaction.getId());
				creditCardNoLbl = new Label("creditCardNoLbl", transaction.getCreditCard().getMaskedNumber());
				cardTypeLbl = new Label("cardTypeLbl", transaction.getCreditCard().getCardType());
				
//				setResponsePage(ProductsPage.class);
//				
////				Subscriber subscriber = subscriberService.createSubscriberAndCompany(name, email, pass, companyName, www);
////				setResponsePage(ClientRegisteredSuccess.class);
				
			} else {
				
				errorLbl = new Label("errorLbl", "Error occurred. Info: " + info);
				
				// gateway,
				// result.getParameters(),
				// result.getErrors()
			}

//			add(amountLbl);
//			add(transIdLbl);
//			add(creditCardNoLbl);
//			add(cardTypeLbl);
//			add(errorLbl);
			
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
