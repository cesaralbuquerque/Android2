package com.synergy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.Purchase;

public interface PurchaseService extends BaseService<Purchase> {

	@Transactional
	Purchase purchaseProduct(Company company, Product product, int quantity, float total, String description, String subscriptionId);

	@Transactional
	void cancelPurchase(Purchase purchase);

	List<Purchase> getPurchaseByProduct(Company company, Product product);

	List<Purchase> getPurchaseByCompany(Company company);

	Integer getNumberOfPurchasedSeat(Company company, Product product);

}
