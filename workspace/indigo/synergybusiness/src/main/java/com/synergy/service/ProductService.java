package com.synergy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;
import com.synergy.vo.ProductInUse;

public interface ProductService extends BaseService<Product> {

	@Transactional
	List<Product> updateProducts(Subscriber subscriber, List<Product> products);

	List<ProductInUse> getProductsInUse(Company company);

	List<Product> getProductsCompany(Company company);

	List<Product> getProducts(Subscriber subscriber);
	
	Product getProductByName(String name);

	List<Product> getAll();

}
