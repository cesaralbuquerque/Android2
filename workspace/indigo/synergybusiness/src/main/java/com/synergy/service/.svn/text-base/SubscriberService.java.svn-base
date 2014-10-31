package com.synergy.service;

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;

public interface SubscriberService extends BaseService<Subscriber> {

	Subscriber authenticate(String email, String password);

	@Transactional
	boolean emailPassword(String email);

	void addToCRM(Subscriber subscriber);

	boolean emailInUse(String email);

	boolean isAdmin(Subscriber subscriber, Company company);

	@Transactional
	Subscriber createCompanyIndependentSubscriber(Subscriber subscriber, String companyName, String website);

	@Transactional
	Subscriber addToCompany(Company company, Collection<Group> groups, Subscriber sub);

	@Transactional
	Subscriber createIndependentSubscriber(String name, String email);

	@Transactional
	Subscriber create(Company company, Collection<Group> groups, String name, String email, String password);

	@Transactional
	Subscriber createSubscriberAndCompany(String name, String email, String password, String companyName, String website);

	@Transactional
	Subscriber createSubscriberAndCompany(String name, String email, String password, String companyName, String website, String phone, boolean confirmedStatus);

	@Transactional
	Subscriber createSubscriberAndCompany(String name, String email, String password, String companyName, String website, String phone);

	List<Subscriber> subscribersFromClient(Company client);

	void emailJoinCompanyInvite(Subscriber subscriber, Company company, Collection<Group> groups, Collection<Product> products);

	void emailUserCreated(Subscriber sub, String pass);

	Subscriber getByEmail(String email);
}
