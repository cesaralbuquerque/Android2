package com.synergy.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.synergy.dao.ChatDao;
import com.synergy.model.Chat;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;

public class ChatDaoImpl extends AbstractBasicHibernateDao<Chat> implements ChatDao {

	public Collection<Chat> searchChat(Company client, Subscriber subscriber, String text) {
		// TODO - change it to a query, and also add the search option to search
		// inside the user name and email.
		Collection<Chat> ret = new ArrayList<Chat>();
		List<Chat> chats = createQuery("from Chat order by date desc").getResultList();
		for (Chat chat : chats) {
			if (subscriber != null) {
				if (chat.getUsers().contains(subscriber)) {
					hasText(text, ret, chat);
				}
			} else {
				hasText(text, ret, chat);
			}
		}
		return ret;
	}
	
	public Collection<Chat> findAll(){
		return createQuery("from Chat order by date desc").getResultList();
	}

	private void hasText(String text, Collection<Chat> ret, Chat chat) {
		if (text == null || chat.getChatlog() != null && chat.getChatlog().toUpperCase().indexOf(text.toUpperCase()) > -1) {
			ret.add(chat);
		}
	}

}
