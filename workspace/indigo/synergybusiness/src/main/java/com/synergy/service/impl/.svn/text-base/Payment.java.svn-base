package com.synergy.service.impl;

import java.math.BigDecimal;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;

public class Payment {
	private BraintreeGateway gateway;
	private String amount;

	public String getTrUrl() {
		return gateway.transparentRedirect().url();
	}

	public String getTrData() {
		TransactionRequest trRequest = new TransactionRequest()
				.type(Transaction.Type.SALE)
				.amount(new BigDecimal(getAmount())).options()
				.submitForSettlement(true).done();

		return gateway.trData(trRequest,
				"http://127.0.0.1:8081/synergyadmin/payment-confirm");
	}

	public BraintreeGateway getGateway() {
		return gateway;
	}

	public void setGateway(BraintreeGateway gateway) {
		this.gateway = gateway;
	}

	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
