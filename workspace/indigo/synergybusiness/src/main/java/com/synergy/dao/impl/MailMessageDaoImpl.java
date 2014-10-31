package com.synergy.dao.impl;

import java.util.Collection;
import java.util.List;

import com.synergy.dao.MailMessageDao;
import com.synergy.model.InboxMailMessage;
import com.synergy.model.MailMessage;
import com.synergy.model.SentMailMessage;
import com.synergy.model.Subscriber;

public class MailMessageDaoImpl extends AbstractBasicHibernateDao<MailMessage> implements MailMessageDao {

	public Collection<MailMessage> findAll() {
		return createQuery("from MailMessage order by dateCreated desc").getResultList();
	}

	public List<InboxMailMessage> getInboxMailMessage(Subscriber subscriber) {
		return (List<InboxMailMessage>) createNamedQuery("InboxMailMessage.getMessages", subscriber).getResultList();
	}

	public List<SentMailMessage> getSentMailMessage(Subscriber subscriber) {
		return (List<SentMailMessage>) createNamedQuery("SentMailMessage.getMessages", subscriber).getResultList();
	}

}
