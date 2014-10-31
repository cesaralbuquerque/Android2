package com.synergy.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.mail.SimpleEmail;

import com.synergy.dao.CompanyDao;
import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.service.CompanyService;
import com.synergy.service.GroupService;
import com.synergy.util.CipherUtil;
import com.synergy.util.SynergyConfig;
import com.synergy.util.SynergyMailUtil;

public class CompanyServiceImpl implements CompanyService {

	private CompanyDao companyDao;

	private GroupService groupService;

	public void refresh(Company user) {
		companyDao.refresh(user);
	}

	public void save(Company user) {
		if (user.getId() == null) {
			companyDao.save(user);
		} else {
			companyDao.merge(user);
		}
	}

	public Company find(Long id) {
		return companyDao.find(id);
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(final CompanyDao dao) {
		this.companyDao = dao;
	}

	public void delete(Company t) {
		this.companyDao.delete(t);
	}

	public List<Company> findAll() {
		return this.companyDao.findAll();
	}

	public Company create(String name, String website, String email) {
		return create(name, website, email, null);
	}

	public Company create(String name, String website, String email, String phone) {
		return create(name, website, email, phone, false);
	}

	public Company create(String name, String website, String email, String phone, boolean confirmedStatus) {
		// create the company
		Company company = new Company();
		company.setCompanyName(name);
		company.setWebsite(website);
		company.setEmail(email);
		company.setPhone(phone);
		company.setDateCreated(new Date());
		if (confirmedStatus) {
			company.setConfirmStatus(Company.CONFIRMED);
		}
		this.companyDao.save(company);

		// create the default group
		Group group = new Group();
		group.setEmail(email);
		group.setName("Default");
		group.setPrivacy(Group.PRIVATE);
		group.setCompany(company);
		company.getGroups().add(group);
		groupService.save(group);

		// create the admin group
		Group groupAdmin = new Group();
		groupAdmin.setEmail(email);
		groupAdmin.setName("Admin");
		groupAdmin.setPrivacy(Group.ADMIN);
		groupAdmin.setCompany(company);
		company.getGroups().add(groupAdmin);
		groupService.save(group);

		return company;
	}

	public void emailConfirmation(Company client) {
		StringBuilder sb = new StringBuilder();
		// sb.append("<html><body>");
		sb.append("Thank you for registering for SynergyChat!");
		sb.append("\n\nYou must first activate your account and then login to your main account in order to configure your groups as well as contacts. After configuring your contacts you can then create your personalized website button for installation.");
		sb.append("\n\nTo activate your account, click the link below:\n");
		final SynergyConfig instance = SynergyConfig.instance();
		final String adminSectionHost = instance.getAdminSectionHost();
		String clientId = client.getId().toString();
		try {
			clientId = CipherUtil.encrypt(clientId);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
		sb.append(adminSectionHost).append("/ui?actAccount=true&cid=").append(clientId);
		sb.append("\n\nPlease visit http://www.synergychat.com/html/GettingStartedonSynergychat.html for detailed instructions. You can also email us at support@synergychat.com or chat live with us Monday-Friday 10am-5pm Est.");
		sb.append("\n\nThank You,");
		sb.append("\nSynergyChat Support Team");
		// sb.append("</body></html>");
		try {
			final SimpleEmail email = SynergyMailUtil.createSupportSimpleMail("SynergyChat Support", "Welcome to SynergyChat", sb.toString(), client.getEmail(), client.getCompanyName());
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

}
