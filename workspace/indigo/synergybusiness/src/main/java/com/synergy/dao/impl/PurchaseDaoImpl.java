package com.synergy.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.synergy.dao.PurchaseDao;
import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.Purchase;

public class PurchaseDaoImpl extends AbstractBasicHibernateDao<Purchase> implements PurchaseDao {

	public List<Purchase> getPurchaseByCompany(Company company) {
		try {
			final Query q = createNamedQuery("Purchase.getByCompany", company);
			return (List<Purchase>) q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Purchase> getPurchaseByProduct(Company company, Product product) {
		try {
			final Query q = createNamedQuery("Purchase.getByProduct", company, product);
			return (List<Purchase>) q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
