package com.synergy.service.impl;

import java.util.Collection;
import java.util.Date;

import com.synergy.dao.ChatDao;
import com.synergy.model.Chat;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;
import com.synergy.model.User;
import com.synergy.service.ChatService;

public class ChatServiceImpl implements ChatService {

	private ChatDao chatDao;

	public Chat createChat(Collection<User> users, String msg, Company client) {
		Chat chat = new Chat();
		chat.setChatlog(msg);
		chat.setClient(client);
		chat.setDate(new Date());
		chat.setUsers(users);
		chatDao.save(chat);
		return chat;
	}

	public Collection<Chat> searchChat(Company client, Subscriber subscriber, String text) {
		return chatDao.searchChat(client, subscriber, text);
	}

	public void delete(Chat t) {
		// TODO Auto-generated method stub

	}
	
	public Collection<Chat> findAll(){
		return chatDao.findAll();
	}

	public Chat find(Long id) {
		return chatDao.find(id);
	}

	public void save(Chat t) {
		chatDao.save(t);
	}

	public void setChatDao(ChatDao chatDao) {
		this.chatDao = chatDao;
	}

	public ChatDao getChatDao() {
		return chatDao;
	}

}
