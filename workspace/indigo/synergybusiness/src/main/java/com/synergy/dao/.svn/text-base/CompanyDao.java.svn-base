package com.synergy.dao;

import java.util.List;

import com.synergy.model.Company;
import com.synergy.model.Subscriber;

public interface CompanyDao extends BaseDao<Company> {
	List<Company> findAll();
	
	Company getFromOwner(Subscriber subscriber);

	Company findByEmail(String address);
	
	Company findByUsername(String username);
	
	Company findByWWW(String www);

	Company findByPersonalPage(String page);

}
