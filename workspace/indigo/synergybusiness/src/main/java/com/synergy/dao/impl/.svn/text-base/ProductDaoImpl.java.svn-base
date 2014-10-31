package com.synergy.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.synergy.dao.ProductDao;
import com.synergy.model.Product;

public class ProductDaoImpl extends AbstractBasicHibernateDao<Product> implements ProductDao {

	public List<Product> getAll() {
		return createQuery("from Product").getResultList();
	}


	public Product getProductByName(String name) {
		final Query q = createNamedQuery("Product.getByName", name);
		return (Product) q.getSingleResult();
	}	

}
