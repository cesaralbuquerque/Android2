package com.synergy.dao;

import java.util.List;

import com.synergy.model.Product;

public interface ProductDao extends BaseDao<Product> {

	
	public List<Product> getAll();
	
	Product getProductByName(String name);

}
