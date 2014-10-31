package com.synergy.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

import com.synergy.dao.MailMessageDao;
import com.synergy.dao.VideoDao;
import com.synergy.model.InboxMailMessage;
import com.synergy.model.MailContact;
import com.synergy.model.MailMessage;
import com.synergy.model.SentMailMessage;
import com.synergy.model.Subscriber;
import com.synergy.model.Video;
import com.synergy.service.MailContactService;
import com.synergy.service.MailMessageService;
import com.synergy.service.SubscriberService;

public class MailMessageServiceImpl implements MailMessageService {

	private MailMessageDao messageDao;

	private VideoDao videoDao;

	private SubscriberService subscriberService;

	private MailContactService mailContactService;

	public void setMailContactService(MailContactService mailContactService) {
		this.mailContactService = mailContactService;
	}

	public MailContactService getMailContactService() {
		return mailContactService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public MailMessage find(Long id) {
		return messageDao.find(id);
	}

	public void save(MailMessage t) {
		messageDao.save(t);
	}

	public void setMessageDao(MailMessageDao videoDao) {
		this.messageDao = videoDao;
	}

	public MailMessageDao getMessageDao() {
		return messageDao;
	}

	public Collection<MailMessage> findAll() {
		return messageDao.findAll();
	}

	public List<InboxMailMessage> getInboxMailMessage(Subscriber subscriber) {
		return messageDao.getInboxMailMessage(subscriber);
	}

	public List<SentMailMessage> getSentMailMessage(Subscriber subscriber) {
		return messageDao.getSentMailMessage(subscriber);
	}

	public void delete(MailMessage msg) {
		if (msg instanceof SentMailMessage) {
			final Video video = msg.getVideo();
			if (video != null) {
				// if it's a sent message, and the owner of the msg is the owner
				// of the video
				// the video is removed
				if (msg.getOwner().equals(video.getSubscriber())) {
					video.setDeleted(true);
					videoDao.save(video);
				}
			}
		}
		messageDao.delete(msg);
	}

	private List<MailContact> createMailContacts(Subscriber sub, InternetAddress[] addrs) {
		List<MailContact> ret = new ArrayList<MailContact>();
		// try{
		// InternetAddress[] addrs = InternetAddress.parse(to);
		for (int i = 0; i < addrs.length; i++) {
			MailContact cont = mailContactService.getByEmail(sub, addrs[i].getAddress());
			if (cont == null) {
				cont = mailContactService.create(sub, addrs[i].getPersonal(), addrs[i].getAddress());
			}
			ret.add(cont);
		}
		// } catch (AddressException e) {
		// e.printStackTrace();
		// }
		return ret;
	}

	public SentMailMessage sendVideoMessage(Subscriber owner, InternetAddress[] to, String subject, String message, Video video) {
		List<MailContact> mailContacts = createMailContacts(owner, to);
		StringBuffer sbEmail = new StringBuffer();
		// for (int i = 0; i < to.length; i++) {
		// sbEmail.append(to[i].t)
		// }
		sbEmail.append(InternetAddress.toString(to));

		SentMailMessage sent = new SentMailMessage();
		sent.setDateCreated(new Date());
		sent.setSubject(subject);
		sent.setEmailsTo(sbEmail.toString());
		sent.setMessage(message);
		sent.setVideo(video);
		sent.setOwner(owner);
		//owner.getMailMessages().add(sent);
		messageDao.save(sent);

		// TODO - refatorar o codigo, para o objeto SentMail e InboxMail apontar
		// para MailMessage, dessa forma pode
		// se aproveitar os atribuots do MailMessage, nao precisam estar
		// duplicados no BD
		for (MailContact mail : mailContacts) {
			// TODO - rever isto, acho q vai ser preciso criar uma company temp,
			// para usuarios desse caso.
			Subscriber sub = subscriberService.getByEmail(mail.getEmail());
			if (sub == null) {
				sub = subscriberService.createIndependentSubscriber("", mail.getEmail());
			}
			InboxMailMessage inbox = new InboxMailMessage();
			inbox.setDateCreated(new Date());
			inbox.setSubject(subject);
			inbox.setFrom(owner);
			inbox.setMessage(message);
			inbox.setVideo(video);
			inbox.setOwner(sub);
			sub.getMailMessages().add(inbox);
			messageDao.save(inbox);
		}

		// TODO - create code to send the email, with the link

		return sent;
	}

	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}

	public VideoDao getVideoDao() {
		return videoDao;
	}

}
