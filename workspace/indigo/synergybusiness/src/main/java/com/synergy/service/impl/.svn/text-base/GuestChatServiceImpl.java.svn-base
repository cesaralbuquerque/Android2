package com.synergy.service.impl;

import java.util.Date;
import java.util.List;

import com.synergy.dao.GuestChatDao;
import com.synergy.model.Company;
import com.synergy.model.GuestChat;
import com.synergy.model.User;
import com.synergy.service.GuestChatService;

public class GuestChatServiceImpl implements GuestChatService {

	private GuestChatDao guestChatDao;
	
	public List<GuestChat> getGuestChatFromClient(Company c){
		return guestChatDao.getGuestChatFromClient(c);
	}

	public GuestChat createGuestChat(String name, String email, Company company) {
		GuestChat guestChat = new GuestChat();
		guestChat.setName(name);
		guestChat.setEmail(email);
		guestChat.setCompany(company);
		guestChat.setDateCreated(new Date());
		save(guestChat);
		return guestChat;
	}

	public void delete(GuestChat t) {
		guestChatDao.delete(t);
	}

	public GuestChat find(Long id) {
		return guestChatDao.find(id);
	}

	public void save(GuestChat t) {
		guestChatDao.save(t);
	}

	public void setGuestChatDao(GuestChatDao guestChatDao) {
		this.guestChatDao = guestChatDao;
	}

	public GuestChatDao getGuestChatDao() {
		return guestChatDao;
	}

}
