package com.synergy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.Company;

public interface CompanyService extends BaseService<Company> {

	@Transactional
	Company create(String name, String website, String email);

	@Transactional
	Company create(String name, String website, String email, String phone, boolean confirmedStatus);

	List<Company> findAll();

	void refresh(Company user);

	public void emailConfirmation(Company client);
}
