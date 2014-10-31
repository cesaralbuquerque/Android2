package com.synergy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.Company;
import com.synergy.model.GuestChat;

public interface GuestChatService extends BaseService<GuestChat> {
	
	@Transactional
	GuestChat createGuestChat(String name, String email, Company company);
	
	public List<GuestChat> getGuestChatFromClient(Company c);
	
}
