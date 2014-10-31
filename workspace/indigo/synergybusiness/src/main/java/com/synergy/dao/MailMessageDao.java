package com.synergy.dao;

import java.util.Collection;
import java.util.List;

import com.synergy.model.InboxMailMessage;
import com.synergy.model.MailMessage;
import com.synergy.model.SentMailMessage;
import com.synergy.model.Subscriber;

public interface MailMessageDao extends BaseDao<MailMessage> {

	public Collection<MailMessage> findAll();

	public List<InboxMailMessage> getInboxMailMessage(Subscriber subscriber);

	public List<SentMailMessage> getSentMailMessage(Subscriber subscriber);

}
