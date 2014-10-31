package com.synergy.service;

import org.springframework.stereotype.Service;

import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.braintreegateway.Transaction;
import com.synergy.service.impl.Payment;

@Service
public interface BrainTreeService {

	public Result<Transaction> purchase(String queryString) throws Exception;

	public Customer getCustomer(String id);

	public Payment newPayment(Double amount) throws Exception;

	public Result<Customer> createCustomerWithNameAndEmail(String customerName, String email);

	public Result<Customer> addCreditCard(String customerId, String cardHolderName, String creditCardNo, String cardExpiryDate, String cardCvvCode);

	public Result<Customer> createCustomerWithCreditCard(String customerName, String creditCardNo, String cardExpiryDate, String cardCvvCode);

	public Result<Subscription> subscribe(String paymentToken, String planId);

	public Result<Customer> updateCustomerId(String oldId, String newId);

	Result<Subscription> cancelSubscribe(String subscribeId);
}
