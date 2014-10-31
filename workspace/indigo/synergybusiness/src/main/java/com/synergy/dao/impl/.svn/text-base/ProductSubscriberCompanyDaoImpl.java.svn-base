package com.synergy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.synergy.dao.ProductSubscriberCompanyDao;
import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.ProductSubscriber;
import com.synergy.model.Subscriber;

public class ProductSubscriberCompanyDaoImpl extends AbstractBasicHibernateDao<ProductSubscriber> implements ProductSubscriberCompanyDao {

	public ProductSubscriber get(Subscriber subscriber) {
		try {

			final Query q = createNamedQuery("ProductSubscriber.get", subscriber);
			return (ProductSubscriber) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Product> getProductsCompany(Company company) {
		final Query q = createNamedQuery("ProductSubscriber.getProductsCompany", company);
		return new ArrayList<Product>(q.getResultList());
	}

	public List<Product> getProducts(Subscriber subscriber) {
		final Query q = createNamedQuery("ProductSubscriber.getProducts", subscriber);
		return new ArrayList<Product>(q.getResultList());
	}

}
