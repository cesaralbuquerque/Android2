package com.synergy.dao.impl;

import java.util.List;

import com.synergy.dao.CompanyDao;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;

public class CompanyDaoImpl extends AbstractBasicHibernateDao<Company> implements CompanyDao {
	@SuppressWarnings( { "unchecked" })
	public List<Company> findAll() {
		return createQuery("from Client order by id desc").getResultList();
	}

	public Company getFromOwner(Subscriber subscriber) {
		return (Company) createNamedQuery("Company.getFromOwner", subscriber).getSingleResult();
	}

	public Company findByEmail(String email) {
		List<Company> users = list("Client.byEmail", email);
		return !users.isEmpty() ? users.get(0) : null;
	}

	public Company findByUsername(String username) {
		List<Company> users = list("Client.byUsername", username);
		return !users.isEmpty() ? users.get(0) : null;
	}

	public Company findByWWW(String www) {
		List<Company> users = list("Client.byWWW", www);
		return !users.isEmpty() ? users.get(0) : null;
	}

	public Company findByPersonalPage(String page) {
		List<Company> users = list("Client.byPersonalPage", page);
		return !users.isEmpty() ? users.get(0) : null;
	}
}
