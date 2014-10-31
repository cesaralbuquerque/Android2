package com.synergy.dao;

import java.util.List;

import com.synergy.model.MailContact;
import com.synergy.model.Subscriber;

public interface MailContactDao extends BaseDao<MailContact>{
    
	List<MailContact> getAll(Subscriber sub);
	
	List<MailContact> searchNameEmail(Subscriber sub, String search);

	MailContact getByEmail(Subscriber sub, String email);

}
