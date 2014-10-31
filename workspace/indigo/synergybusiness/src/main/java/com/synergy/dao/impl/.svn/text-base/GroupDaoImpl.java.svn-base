package com.synergy.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.synergy.dao.GroupDao;
import com.synergy.model.Company;
import com.synergy.model.Group;

public class GroupDaoImpl extends AbstractBasicHibernateDao<Group> implements GroupDao {

	public Group findByNameAndCompany(String name, Company client) {
		List<Group> groups = list("Group.byNameAndCompany", name, client);
		return !groups.isEmpty() ? groups.get(0) : null;
	}

	public List<Group> groupsFromCompany(Company client) {
		Query q = createQuery("from Group g where g.company=? and g.deleted is false");
		q.setParameter(1, client);
		return q.getResultList();
	}

}
