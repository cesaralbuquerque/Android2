package com.synergy.dao.impl;

import java.util.List;

import com.synergy.dao.GuestChatDao;
import com.synergy.model.Company;
import com.synergy.model.GuestChat;

public class GuestChatDaoImpl extends AbstractBasicHibernateDao<GuestChat> implements GuestChatDao {

	public List<GuestChat> getGuestChatFromClient(Company c) {
		return list("GuestChat.fromCompany", c);
	}

}
