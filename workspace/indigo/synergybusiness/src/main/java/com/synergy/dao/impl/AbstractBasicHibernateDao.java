package com.synergy.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractBasicHibernateDao<T> {
	private static final Log log = LogFactory.getLog(AbstractBasicHibernateDao.class);
	private Class _clazz;

	private EntityManager manager;

	public AbstractBasicHibernateDao() {
		try {
			_clazz = Class.forName(getClass().getGenericSuperclass().toString().split("[<>]")[1]);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public void save(T obj) {
		getEntityManager().persist(obj);
	}

	@SuppressWarnings("unchecked")
	public T find(Long id) {
		return (T) getEntityManager().find(_clazz, id);
	}

	public void refresh(T obj) {
		getEntityManager().refresh(obj);
	}

	public void merge(T obj) {
		getEntityManager().merge(obj);
	}

	public void delete(T obj) {
		obj = getEntityManager().merge(obj);
		getEntityManager().remove(obj);
	}

	protected EntityManager getEntityManager() {
		return manager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		manager = em;
	}

	protected Query createQuery(String queryString) {
		return getEntityManager().createQuery(queryString);
	}

	protected Query createNamedQuery(String query, Object... params) {
		Query q = getEntityManager().createNamedQuery(query);
		for (int i = 1; i <= params.length; i++) {
			q.setParameter(i, params[i - 1]);
		}
		return q;
	}

	@SuppressWarnings( { "unchecked" })
	protected List<T> list(String name) {
		return (List<T>) getEntityManager().createNamedQuery(name).getResultList();
	}

	@SuppressWarnings( { "unchecked" })
	protected List<T> list(String name, Object... params) {
		return (List<T>) createNamedQuery(name, params).getResultList();
	}
}
