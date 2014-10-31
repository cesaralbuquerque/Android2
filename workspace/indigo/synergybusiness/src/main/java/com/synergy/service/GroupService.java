package com.synergy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.Company;
import com.synergy.model.Group;

public interface GroupService extends BaseService<Group> {

	boolean nameInUse(String name, Company client);

	List<Group> groupsFromCompany(Company client);

	void refresh(Group group);

	@Transactional
	Group create(String name, Company client);

}
