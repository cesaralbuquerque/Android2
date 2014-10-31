package com.synergy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synergy.dao.ProductDao;
import com.synergy.dao.ProductSubscriberCompanyDao;
import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.ProductSubscriber;
import com.synergy.model.Subscriber;
import com.synergy.service.ProductService;
import com.synergy.vo.ProductInUse;

public class ProductServiceImpl implements ProductService {

	private ProductSubscriberCompanyDao subCompDao;

	private ProductDao productDao;

	public List<Product> getAll() {
		return productDao.getAll();
	}

	public List<Product> getProducts(Subscriber subscriber) {
		return subCompDao.getProducts(subscriber);
		// ProductSubscriber sc = subCompDao.get(subscriber);
		// if (sc == null) {
		// return Collections.EMPTY_LIST;
		// }
		// return new ArrayList<Product>(sc.getProducts());
	}

	public List<ProductInUse> getProductsInUse(Company company) {
		HashMap<Product, Integer> map = new HashMap<Product, Integer>();
		List<Product> l = getProductsCompany(company);
		for (Product product : l) {
			Integer i = map.get(product);
			if (i == null) {
				i = new Integer(0);
			}
			i++;
			map.put(product, i);
		}
		List<ProductInUse> ret = new ArrayList<ProductInUse>();
		for (Map.Entry<Product, Integer> m : map.entrySet()) {
			ret.add(new ProductInUse(m.getKey(), m.getValue()));
		}
		return ret;
	}

	public List<Product> getProductsCompany(Company company) {
		return subCompDao.getProductsCompany(company);
	}

	public List<Product> updateProducts(Subscriber subscriber, List<Product> products) {
		ProductSubscriber sc = subCompDao.get(subscriber);
		if (sc == null) {
			sc = new ProductSubscriber();
			sc.setSubscriber(subscriber);
		} else {
			if (products.equals(sc.getProducts())) {
				// if the list is the same, it doesn't need to be saved
				return products;
			}
		}
		sc.setProducts(products);
		// sc.getProducts().addAll(products);
		// sc.setProducts(products);
		subCompDao.save(sc);
		// return new ArrayList<Product>(sc.getProducts());
		return getProducts(subscriber);
	}

	public void delete(Product t) {
		// subCompDao.delete(t);
	}

	public Product find(Long id) {
		return productDao.find(id);
	}

	public void save(Product t) {
		productDao.save(t);
	}

	public void setSubCompDao(ProductSubscriberCompanyDao subCompDao) {
		this.subCompDao = subCompDao;
	}

	public ProductSubscriberCompanyDao getSubCompDao() {
		return subCompDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public Product getProductByName(String name) {
		return productDao.getProductByName(name);
	}

}
