package com.synergy.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.synergy.dao.SubscriberDao;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;
import com.synergy.util.Util;

public class SubscriberDaoImpl extends AbstractBasicHibernateDao<Subscriber> implements SubscriberDao {
	@SuppressWarnings( { "unchecked" })
	public List<Subscriber> findAll() {
		return createQuery("from Subscriber").getResultList();
	}

	public Subscriber findByEmail(String email) {
		List<Subscriber> users = list("Subscriber.byEmail", email);
		return !users.isEmpty() ? users.get(0) : null;
	}

	public Subscriber authenticate(String email, String pass) {
		byte[] hashed = Util.digest(pass);
		getEntityManager().clear();
		Query q = createNamedQuery("Subscriber.authenticate", email, hashed);
		// q.setParameter(1, email);
		// q.setParameter(2, hashed);
		try {
			return (Subscriber) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Subscriber> subscribersFromClient(Company client) {
		Query q = createQuery("from Subscriber g where g.deleted is false and g.company=?");
		q.setParameter(1, client);
		return q.getResultList();
	}
}
