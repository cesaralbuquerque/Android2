package com.synergy.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.synergy.model.Chat;
import com.synergy.model.GuestChat;
import com.synergy.model.Subscriber;
import com.synergy.model.User;
import com.synergy.service.ChatService;
import com.synergy.service.GuestChatService;
import com.synergy.service.SubscriberService;
import com.synergy.util.SynergyConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestChat {

	private ChatService chatService;

	private SubscriberService subscriberService;

	private GuestChatService guestChatService;

	private EntityManagerFactory entityManagerFactory;

	@Test
	public void setUp() throws IOException {
		final InputStream in = this.getClass().getClassLoader().getResourceAsStream("synergyengine.properties");
		if (in == null) {
			Assert.fail("synergyengine.properties not found");
		}
		SynergyConfig.instance().load(in);

		Subscriber s = subscriberService.createSubscriberAndCompany("Fabiano", "fabiano@test.com", "f", "Fabiano Company", "website");
		subscriberService.create(s.getCompany(), s.getGroups(), "Brian", "brian@test.com", "b");
	}

	@Test
	public void testChat() {
		Subscriber fabiano = subscriberService.getByEmail("fabiano@test.com");
		Subscriber brian = subscriberService.getByEmail("brian@test.com");
		ArrayList<User> chats = new ArrayList<User>();
		chats.add(fabiano);
		chats.add(brian);
		final String msg = "Start Chat";
		Chat chat = chatService.createChat(chats, msg, fabiano.getCompany());
		Assert.assertNotNull(chat);

		Assert.assertEquals(chat.getUsers().size(), 2);

		Assert.assertEquals(chat.getChatlog(), msg);
	}

	@Test
	public void testGuestChat() {
		Subscriber fabiano = subscriberService.getByEmail("fabiano@test.com");
		GuestChat guest = guestChatService.createGuestChat("Guest", "g@g.com", fabiano.getCompany());
		Assert.assertNotNull(guest);
		Assert.assertEquals(guest.getCompany(), fabiano.getCompany());

		ArrayList<User> chats = new ArrayList<User>();
		chats.add(fabiano);
		chats.add(guest);
		final String msg = "Start Chat";
		Chat chat = chatService.createChat(chats, msg, fabiano.getCompany());
		Assert.assertNotNull(chat);

		Assert.assertEquals(chat.getUsers().size(), 2);

		Assert.assertEquals(chat.getChatlog(), msg);
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
	public void setGuestChatService(GuestChatService guestChatService) {
		this.guestChatService = guestChatService;
	}

	@Resource
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	@Resource
	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}

}
