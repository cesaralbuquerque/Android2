package com.synergy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.MailContact;
import com.synergy.model.Subscriber;

public interface MailContactService extends BaseService<MailContact> {

	@Transactional
	MailContact create(Subscriber sub, String name, String email);

	public List<MailContact> getAll(Subscriber sub);
	
	List<MailContact> searchNameEmail(Subscriber sub, String search);

	MailContact getByEmail(Subscriber sub, String email);
}
