package com.synergy.service.impl;

import java.util.List;

import com.synergy.dao.MailContactDao;
import com.synergy.model.MailContact;
import com.synergy.model.Subscriber;
import com.synergy.service.MailContactService;

public class MailContactServiceImpl implements MailContactService {
	
	private MailContactDao mailContactDao;

	public MailContact create(Subscriber sub, String name, String email) {
		MailContact mail = new MailContact();
		mail.setEmail(email);
		mail.setName(name);
		mail.setOwner(sub);
		save(mail);
		return mail;
	}
	
	public List<MailContact> getAll(Subscriber sub){
		return mailContactDao.getAll(sub);
	}

	public List<MailContact> searchNameEmail(Subscriber sub, String search) {
		return mailContactDao.searchNameEmail(sub, search);
	}

	public MailContact getByEmail(Subscriber sub, String email) {
		return mailContactDao.getByEmail(sub, email);
	}

	public void setMailContactDao(MailContactDao mailContactDao) {
		this.mailContactDao = mailContactDao;
	}
	
	public MailContactDao getMailContactDao() {
		return mailContactDao;
	}

	public void save(MailContact t) {
		mailContactDao.save(t);
	}

	public void delete(MailContact t) {
		mailContactDao.delete(t);
	}

	public MailContact find(Long id) {
		return mailContactDao.find(id);
	}
}
