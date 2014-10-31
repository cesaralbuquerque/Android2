package com.synergy.dao;

import java.util.List;

import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.ProductSubscriber;
import com.synergy.model.Subscriber;

public interface ProductSubscriberCompanyDao extends BaseDao<ProductSubscriber> {

	ProductSubscriber get(Subscriber subscriber);

	public List<Product> getProducts(Subscriber subscriber);

	public List<Product> getProductsCompany(Company company);

}
