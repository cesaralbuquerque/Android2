package com.synergy.service;

import java.util.Collection;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.InboxMailMessage;
import com.synergy.model.MailMessage;
import com.synergy.model.SentMailMessage;
import com.synergy.model.Subscriber;
import com.synergy.model.Video;

public interface MailMessageService extends BaseService<MailMessage> {

	public Collection<MailMessage> findAll();

	List<InboxMailMessage> getInboxMailMessage(Subscriber subscriber);

	List<SentMailMessage> getSentMailMessage(Subscriber subscriber);

	@Transactional
	SentMailMessage sendVideoMessage(Subscriber owner, InternetAddress[] to, String subject, String message, Video video);

}
