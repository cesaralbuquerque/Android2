package com.synergy.dao;

import java.util.List;

import com.synergy.model.Company;
import com.synergy.model.Subscriber;

public interface SubscriberDao extends BaseDao<Subscriber> {
	
	List<Subscriber> findAll();

	Subscriber findByEmail(String address);

	List<Subscriber> subscribersFromClient(Company client);

	Subscriber authenticate(String email, String pass);
	
}
