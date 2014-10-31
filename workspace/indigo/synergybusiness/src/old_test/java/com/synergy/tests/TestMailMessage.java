package com.synergy.tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.synergy.model.Chat;
import com.synergy.model.Company;
import com.synergy.model.SentMailMessage;
import com.synergy.model.Subscriber;
import com.synergy.model.User;
import com.synergy.model.Video;
import com.synergy.service.ChatService;
import com.synergy.service.CompanyService;
import com.synergy.service.MailMessageService;
import com.synergy.service.SubscriberService;
import com.synergy.service.VideoService;
import com.synergy.util.CipherUtil;
import com.synergy.util.SynergyConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestMailMessage {

	private MailMessageService mailMessageService;

	private CompanyService companyService;

	private VideoService videoService;

	private SubscriberService subscriberService;

	private EntityManagerFactory entityManagerFactory;

	@Test
	public void setUp() throws Exception {
		String videoId = "40_1315061478141";
		System.out.println(CipherUtil.encrypt("18," + videoId + ",-1"));
		
		final InputStream in = this.getClass().getClassLoader().getResourceAsStream("synergyengine.properties");
		if (in == null) {
			Assert.fail("synergyengine.properties not found");
		}
		SynergyConfig.instance().load(in);

		Subscriber s = subscriberService.createSubscriberAndCompany("Fabiano", "fabiano@test.com", "f", "Fabiano Company", "website");
		subscriberService.create(s.getCompany(), s.getGroups(), "Brian", "brian@test.com", "b");

	}

	@Test
	public void testSendMessage() throws AddressException {
		Subscriber owner = subscriberService.getByEmail("fabiano@test.com");
		final InternetAddress[] to = InternetAddress.parse("brian@test.com, new@new.com");
		final Video video = videoService.createVideo(owner, (long) 123, (long) 456, "video_id", null);
		Assert.assertNotNull(video);
		final SentMailMessage sent = mailMessageService.sendVideoMessage(owner, to, "Subject", "Message", video);
		Assert.assertNotNull(sent);
		Assert.assertEquals(1, mailMessageService.getSentMailMessage(owner).size());

		Subscriber brian = subscriberService.getByEmail("brian@test.com");
		Assert.assertTrue(mailMessageService.getSentMailMessage(brian).isEmpty());
		Assert.assertEquals(1, mailMessageService.getInboxMailMessage(brian).size());

		Assert.assertFalse(subscriberService.emailInUse("new@new.com"));
		Assert.assertNotNull(subscriberService.getByEmail("new@new.com"));
	}

	@Test
	public void testCreateCompanyIndependentUser() {
		Assert.assertFalse(subscriberService.emailInUse("new@new.com"));
		Subscriber s = subscriberService.getByEmail("new@new.com");
		Assert.assertNull(s.getCompany());
		s = subscriberService.createCompanyIndependentSubscriber(s, "Company", "mywebsite");
		// Company c = companyService.create("Company", "www", s.getEmail());

		// subscriberService.create(c, c.getGroups(), "", s.getEmail(), "");
		// s = subscriberService.addToCompany(c, c.getGroups(), s);

		Assert.assertNotNull(s.getCompany());
		Assert.assertTrue(subscriberService.emailInUse("new@new.com"));
	}

	@Test
	public void tearDown() {
		// drop the database, and create a new one for the next test
		final EntityManager e = entityManagerFactory.createEntityManager();
		e.getTransaction().begin();
		e.createNativeQuery("DROP DATABASE synergyunittest").executeUpdate();
		e.getTransaction().commit();
		e.getTransaction().begin();
		e.createNativeQuery("CREATE DATABASE synergyunittest").executeUpdate();
		e.getTransaction().commit();
	}

	@Resource
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Resource
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	@Resource
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	@Resource
	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}

}
