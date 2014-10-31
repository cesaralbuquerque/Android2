package com.synergy.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.synergy.dao.MailContactDao;
import com.synergy.dao.SubscriberDao;
import com.synergy.model.Company;
import com.synergy.model.MailContact;
import com.synergy.model.Subscriber;
import com.synergy.util.Util;

public class MailContactDaoImpl extends AbstractBasicHibernateDao<MailContact> implements MailContactDao {

	public List<MailContact> getAll(Subscriber sub) {
		Query q = createNamedQuery("MailContact.getAll", sub);
		return q.getResultList();
	}
	
	public List<MailContact> searchNameEmail(Subscriber sub, String search) {
		Query q = createNamedQuery("MailContact.searchNameEmail", sub, search, search);
		return q.getResultList();
	}

	public MailContact getByEmail(Subscriber sub, String email) {
		Query q = createNamedQuery("MailContact.byEmail", sub, email);
		try {
			return (MailContact) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
