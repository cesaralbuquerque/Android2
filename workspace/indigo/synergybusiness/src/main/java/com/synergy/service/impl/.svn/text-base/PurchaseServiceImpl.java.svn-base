package com.synergy.service.impl;

import java.util.Date;
import java.util.List;

import com.synergy.dao.PurchaseDao;
import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.Purchase;
import com.synergy.service.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {

	private PurchaseDao purchaseDao;

	public void cancelPurchase(Purchase purchase) {
		purchase.setDateCancelled(new Date());
		purchase.setStatus(Purchase.CANCELLED);
		save(purchase);
	}

	public Integer getNumberOfPurchasedSeat(Company company, Product product) {
		final List<Purchase> ps = getPurchaseByProduct(company, product);
		int q = 0;
		for (Purchase purchase : ps) {
			q += purchase.getQuantity();
		}
		return q;
	}

	public List<Purchase> getPurchaseByCompany(Company company) {
		return purchaseDao.getPurchaseByCompany(company);
	}

	public List<Purchase> getPurchaseByProduct(Company company, Product product) {
		return purchaseDao.getPurchaseByProduct(company, product);
	}

	public Purchase purchaseProduct(Company company, Product product, int quantity, float total, String description, String subscriptionId) {
		Purchase purchase = new Purchase();
		purchase.setCompany(company);
		purchase.setProduct(product);
		purchase.setDate(new Date());
		purchase.setQuantity(quantity);
		purchase.setPriceTotal(total);
		purchase.setStatus(Purchase.APPROVED);
		purchase.setDescription(description);
		purchase.setSubscriptionId(subscriptionId);
		save(purchase);
		return purchase;
	}

	public void delete(Purchase t) {

	}

	public Purchase find(Long id) {
		return purchaseDao.find(id);
	}

	public void save(Purchase t) {
		purchaseDao.save(t);
	}

	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	public PurchaseDao getPurchaseDao() {
		return purchaseDao;
	}

}
