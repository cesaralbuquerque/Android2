package com.synergy.service.impl;

import java.util.List;

import com.synergy.dao.GroupDao;
import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.service.GroupService;

public class GroupServiceImpl implements GroupService {

	private GroupDao groupDao;

	public Group create(String name, Company client) {
		Group g = new Group();
		g.setName(name);
		g.setCompany(client);
		groupDao.save(g);
		return g;
	}

	public Group find(Long id) {
		return groupDao.find(id);
	}

	public void refresh(Group group) {
		groupDao.refresh(group);
	}

	public void save(Group t) {
		if (t.getId() == null) {
			groupDao.save(t);
		} else {
			groupDao.merge(t);
		}
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(final GroupDao dao) {
		this.groupDao = dao;
	}

	public boolean nameInUse(String name, Company client) {
		return groupDao.findByNameAndCompany(name, client) != null;
	}

	public List<Group> groupsFromCompany(Company client) {
		final List<Group> groupsFromClient = groupDao.groupsFromCompany(client);
		for (Group group : groupsFromClient) {
			groupDao.refresh(group);
		}
		return groupsFromClient;
	}

	public void delete(Group t) {
		// groupDao.delete(t);
		t.setDeleted(true);
		save(t);
	}

}
