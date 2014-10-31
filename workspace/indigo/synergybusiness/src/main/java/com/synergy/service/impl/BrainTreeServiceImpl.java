package com.synergy.service.impl;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.braintreegateway.SubscriptionRequest;
import com.braintreegateway.Transaction;
import com.synergy.service.BrainTreeService;

public class BrainTreeServiceImpl implements BrainTreeService {

	public Result<Transaction> purchase(String queryString) throws Exception {
		BraintreeGateway gateway = getGateway();

		Result<Transaction> result = gateway.transparentRedirect().confirmTransaction(queryString);

		return result;
	}

	public Payment newPayment(Double amount) throws Exception {

		Payment payment = new Payment();

		payment.setGateway(getGateway());
		payment.setAmount(amount.toString());

		return payment;
	}

	public Customer getCustomer(String id) {
		return getGateway().customer().find(id);
	}

	private BraintreeGateway getGateway() {
		final BraintreeGateway braintreeGateway = BraintreeConfiguration.getBraintreeGateway();
		return braintreeGateway;
	}

	public Result<Customer> createCustomerWithNameAndEmail(String customerName, String email) {

		CustomerRequest request = new CustomerRequest().firstName(customerName).email(email);

		Result<Customer> result = getGateway().customer().create(request);

		return result;
	}

	public Result<Customer> addCreditCard(String customerId, String cardHolderName, String creditCardNo, String cardExpiryDate, String cardCvvCode) {

		CustomerRequest request = new CustomerRequest().id(customerId).creditCard().cardholderName(cardHolderName).number(creditCardNo).expirationDate(cardExpiryDate).done();

		Result<Customer> result = getGateway().customer().update(customerId, request);

		return result;
	}

	public Result<Customer> createCustomerWithCreditCard(String customerName, String creditCardNo, String cardExpiryDate, String cardCvvCode) {

		CustomerRequest request = new CustomerRequest().firstName(customerName).creditCard().cardholderName(customerName).number(creditCardNo).expirationDate(cardExpiryDate).done();

		Result<Customer> result = getGateway().customer().create(request);

		return result;
	}

	public Result<Customer> updateCustomerId(String oldId, String newId) {

		CustomerRequest request = new CustomerRequest().id(newId);

		Result<Customer> updateResult = getGateway().customer().update(oldId, request);

		if (updateResult.isSuccess()) {
			return updateResult;
		}
		return null;
	}

	public Result<Subscription> cancelSubscribe(String subscribeId) {
		Result<Subscription> result = getGateway().subscription().cancel(subscribeId);
		return result;
	}

	public Result<Subscription> subscribe(String paymentToken, String planId) {

		SubscriptionRequest request = new SubscriptionRequest().paymentMethodToken(paymentToken).planId(planId).options().startImmediately(true).done();

		Result<Subscription> result = getGateway().subscription().create(request);
		
		return result;
	}
}
