package com.synergychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IServiceCapableConnection;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.synergy.model.Chat;
import com.synergy.model.Company;
import com.synergy.model.User;
import com.synergy.service.ChatService;
import com.synergy.service.UserService;
import com.synergy.util.SynergyConfig;

/**
 * <code>StreamManager</code> provides services for recording the broadcast
 * stream.
 */
public class VChatManager {

	private static Logger log = Red5LoggerFactory.getLogger(VChatManager.class);

	private ClassLoader refClassLoader = Thread.currentThread().getContextClassLoader();

	private ClassLoader origLoader = Thread.currentThread().getContextClassLoader();

	private void toggleClassLoader() {
		if (refClassLoader == origLoader) {
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		} else {
			Thread.currentThread().setContextClassLoader(origLoader);
		}
	}

	protected EntityManagerFactory lookupEntityManagerFactory() {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		return (EntityManagerFactory) wac.getBean("entityManagerFactory", EntityManagerFactory.class);
	}

	protected EntityManager createEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

	public WebApplicationContext getSpringContext() {
		log.info("getSpringContext");
		ServletContext ctx = getServletContext();
		WebApplicationContext springContext = (WebApplicationContext) ctx.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return springContext;
	}

	public ServletContext getServletContext() {
		log.info("getServletContex");
		ApplicationContext appCtx = app.getContext().getApplicationContext();
		System.out.println("appCtx: " + appCtx);
		ServletContext ctx = ((XmlWebApplicationContext) appCtx).getServletContext();
		System.out.println("ctx: " + ctx);
		return ctx;
	}

	public void closeDbContext(EntityManagerFactory emf) {
		log.info("closeDbContext: "+emf);
		toggleClassLoader();
		if (!participate) {
			log.debug("CLOSE DB CONTEXT");
			EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(emf);
			emHolder.getEntityManager().close();
		}
	}

	public EntityManagerFactory startDbContext() {
		log.info("startDbContext");
		toggleClassLoader();
		EntityManagerFactory emf;
		emf = lookupEntityManagerFactory();
		log.debug("START DB CONTEXT");
		participate = false;
		if (TransactionSynchronizationManager.hasResource(emf)) {
			log.debug("START DB CONTEXT-PARTICIPATE=TRUE");
			participate = true;
		} else {
			try {
				EntityManager em = createEntityManager(emf);
				TransactionSynchronizationManager.bindResource(emf, new EntityManagerHolder(em));
			} catch (PersistenceException ex) {
				log.error(ex.getMessage(), ex);
				throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
			}
		}
		return emf;
	}

	boolean participate;

	// Application components
	private Application app;

	public void inviteFaceToFace(String email) {
		log.info("inviteFaceToFace: "+email);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		UserService userService = (UserService) springContext.getBean("userService");
		String fromUID = (String) conn.getClient().getAttribute("uid");
		log.info("userService.find: "+fromUID);
		User user = userService.find(Long.parseLong(fromUID));
		final SimpleEmail simpleEmail = new SimpleEmail();
		try {
			simpleEmail.setHostName("mail.synergychat.com");
			simpleEmail.setAuthentication("fabianomodos@synergychat.com", "jApsu72nk");
			simpleEmail.addTo(email, "");
			simpleEmail.setFrom("no-reply@synergychat.com", user.getName());
			simpleEmail.setSubject("Chat Invite");
			final Company client = user.getCompany();
			String msg = "The user " + user.getName() + " from the company " + client.getCompanyName() + " is inviting you to chat.";
			msg += "\nClick in the link below to enter this chat:";
			msg += "\n"+SynergyConfig.instance().getAdminSectionHost()+"/ui?chatInvite=" + user.getId();
			simpleEmail.setMsg(msg);
			simpleEmail.send();
		} catch (EmailException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public Object chatUser(String toUID, String message, String chatId) {
		log.info("chatUser: "+toUID+", "+message+", "+chatId);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		UserService userService = (UserService) springContext.getBean("userService");
		ChatService chatService = (ChatService) springContext.getBean("chatService");

		String fromUID = (String) conn.getClient().getAttribute("uid");
		String nickname = (String) conn.getClient().getAttribute("nickname");
		message = new StringBuffer(nickname).append(": ").append(message).toString();

		Chat chat = null;
		ArrayList<String> usersId = new ArrayList<String>();
		// if this chatId is null then search for one chatId saved in the
		// client.
		// TODO-Maybe in the future can try to find the client of the toUID and
		// then search for a chatId in the inverse side too.
		chatId = (chatId == null || chatId.length() == 0) ? (String) conn.getClient().getAttribute(toUID) : chatId;

		if (chatId == null || chatId.length() == 0) {
			// create a new chatId.
			ArrayList<User> users = new ArrayList<User>();
			log.info("userService.find: "+fromUID);
			User fromUser = userService.find(Long.parseLong(fromUID));
			users.add(fromUser);
			// usersId.add(curUser.getId().toString());
			String[] sp = toUID.split(";");
			for (int i = 0; i < sp.length; i++) {
				log.info("userService.find: "+sp[i]);
				User curUser = userService.find(Long.parseLong(sp[i]));
				users.add(curUser);
				usersId.add(curUser.getId().toString());
			}

			String saveMsg = message;
			if (message.indexOf("$#$msg-cmd$#$:") != -1) {
				saveMsg = "";
			}
			log.info("chatService.createChat: "+users+", "+saveMsg+", "+fromUser.getCompany());
			chat = chatService.createChat(users, saveMsg, fromUser.getCompany());
			chatId = chat.getId().toString();
		} else {
			// don't save cmd msg.
			log.info("chatService.find: "+chatId);
			chat = chatService.find(Long.parseLong(chatId));
			if (message.indexOf("$#$msg-cmd$#$:") == -1) {
				chat.setChatlog(new StringBuffer().append(chat.getChatlog()).append("\n").append(message).toString());
				chatService.save(chat);
			}
			// TODO - Optimize to load only the id
			for (User user : chat.getUsers()) {
				usersId.add(user.getId().toString());
			}
			usersId.remove(fromUID);
		}
		// link the toUID with this client and the chatId.
		conn.getClient().setAttribute(toUID, chatId);

		boolean messageDelivered = false;
		Iterator<IConnection> it = getConnections(conn.getScope().getConnections());
		while (it.hasNext()) {
			IConnection conn2 = it.next();
			if (conn2 instanceof IServiceCapableConnection) {
				IServiceCapableConnection service = (IServiceCapableConnection) conn2;
				IClient client = service.getClient();
				Object curUid = client.getAttribute("uid");

				if (usersId.contains(curUid)) {
					// link the client with the fromUID and the chatId. So it
					// uses the same chatId while the user is online.
					client.setAttribute(fromUID, chatId);
					messageDelivered = true;
					log.info("invoke.messageReceived: "+fromUID+", "+nickname+", "+message+", "+chatId);
					service.invoke("messageReceived", new Object[] { fromUID, nickname, message, chatId });
				}
			}
		}

		closeDbContext(emf);
		if (!messageDelivered) {
			// throw new IllegalStateException("The message \"" + message +
			// "\"couldn't be delivered to the user.");
			throw new IllegalStateException("user_offline");
		}
		return chatId;
	}

	public Object sendMessageWithType(String chatId, Boolean isConf, String message, int serverMessageType) {
		log.info("sendMessageWithType: "+chatId+", "+isConf+", "+message+", "+serverMessageType);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		ChatService chatService = (ChatService) springContext.getBean("chatService");

		String fromUID = (String) conn.getClient().getAttribute("uid");
		String nickname = (String) conn.getClient().getAttribute("nickname");
		long idLong = -1;
		if (isConf) {
			idLong = Long.parseLong(chatId, Character.MAX_RADIX);
		} else {
			idLong = Long.parseLong(chatId);
		}
		log.info("chatService.find: "+idLong);
		Chat chat = chatService.find(idLong);

		ArrayList<String> usersId = new ArrayList<String>();

		for (User user : chat.getUsers()) {
			usersId.add(user.getId().toString());
		}
		usersId.remove(fromUID);

		Iterator<IConnection> it = getConnections(conn.getScope().getConnections());

		while (it.hasNext()) {
			IConnection conn2 = it.next();
			if (conn2 instanceof IServiceCapableConnection) {
				IServiceCapableConnection service = (IServiceCapableConnection) conn2;
				IClient client = service.getClient();
				Object curUid = client.getAttribute("uid");

				if (usersId.contains(curUid)) {
					log.info("invoke.messageReceived: "+fromUID+", "+nickname+", "+message+", "+chatId+", "+serverMessageType);
					service.invoke("messageReceived", new Object[] { fromUID, nickname, message, chatId, serverMessageType });
				}
			}
		}

		closeDbContext(emf);
		return chatId;
	}

	public Object propogateTypingConference(String chatId, Boolean isConf) {
		log.info("propogateTypingConference: "+chatId+", "+isConf);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		ChatService chatService = (ChatService) springContext.getBean("chatService");

		String fromUID = (String) conn.getClient().getAttribute("uid");
		String nickname = (String) conn.getClient().getAttribute("nickname");
		long idLong = -1;
		if (isConf) {
			idLong = Long.parseLong(chatId, Character.MAX_RADIX);
		} else {
			idLong = Long.parseLong(chatId);
		}
		log.info("chatService.find: "+idLong);
		Chat chat = chatService.find(idLong);

		ArrayList<String> usersId = new ArrayList<String>();

		for (User user : chat.getUsers()) {
			usersId.add(user.getId().toString());
		}
		usersId.remove(fromUID);

		Iterator<IConnection> it = getConnections(conn.getScope().getConnections());
		int serverMessageType = 21;

		while (it.hasNext()) {
			IConnection conn2 = it.next();
			if (conn2 instanceof IServiceCapableConnection) {
				IServiceCapableConnection service = (IServiceCapableConnection) conn2;
				IClient client = service.getClient();
				Object curUid = client.getAttribute("uid");

				if (usersId.contains(curUid)) {
					log.info("invoke.messageReceived: "+fromUID+", "+nickname+", "+nickname+", "+chatId+", "+serverMessageType);
					service.invoke("messageReceived", new Object[] { fromUID, nickname, nickname + " is typing a message", chatId, serverMessageType });
				}
			}
		}

		closeDbContext(emf);
		return chatId;
	}

	public Object cancelTyping(String chatId, Boolean isConf) {
		log.info("cancelTyping: "+chatId+", "+isConf);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		ChatService chatService = (ChatService) springContext.getBean("chatService");

		String fromUID = (String) conn.getClient().getAttribute("uid");
		String nickname = (String) conn.getClient().getAttribute("nickname");
		long idLong = -1;
		if (isConf) {
			idLong = Long.parseLong(chatId, Character.MAX_RADIX);
		} else {
			idLong = Long.parseLong(chatId);
		}
		log.info("chatService.find: "+idLong);
		Chat chat = chatService.find(idLong);

		ArrayList<String> usersId = new ArrayList<String>();

		for (User user : chat.getUsers()) {
			usersId.add(user.getId().toString());
		}
		usersId.remove(fromUID);

		Iterator<IConnection> it = getConnections(conn.getScope().getConnections());
		int serverMessageType = 23;

		while (it.hasNext()) {
			IConnection conn2 = it.next();
			if (conn2 instanceof IServiceCapableConnection) {
				IServiceCapableConnection service = (IServiceCapableConnection) conn2;
				IClient client = service.getClient();
				Object curUid = client.getAttribute("uid");

				if (usersId.contains(curUid)) {
					log.info("invoke.messageReceived: "+fromUID+", "+nickname+", "+chatId+", "+serverMessageType);
					service.invoke("messageReceived", new Object[] { fromUID, nickname, "", chatId, serverMessageType });
				}
			}
		}

		closeDbContext(emf);
		return chatId;
	}

	public Object transferChat(String toUID, String message, String oldChatId) {
		log.info("transferChat: "+toUID+", "+message+", "+oldChatId);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		UserService userService = (UserService) springContext.getBean("userService");
		ChatService chatService = (ChatService) springContext.getBean("chatService");
		String fromUID = (String) conn.getClient().getAttribute("uid");
		String nickname = (String) conn.getClient().getAttribute("nickname");
		log.info("chatService.find: "+oldChatId);
		Chat oldChat = chatService.find(Long.parseLong(oldChatId));

		log.info("userService.find: "+toUID);
		User toUser = userService.find(Long.parseLong(toUID));
		User otherUser = null;// userService.find(Long.parseLong(otherUID));
		// get the another user from the chat.
		for (User curUser : oldChat.getUsers()) {
			if (!curUser.getId().toString().equals(fromUID)) {
				otherUser = curUser;
				break;
			}
		}
		String otherUID = otherUser.getId().toString();

		StringBuffer sb = new StringBuffer("The user ").append(nickname).append(" transfered his current chat to you.");
		sb.append("\nBelow is one message from him to you.");
		sb.append("\n\"").append(message).append("\"");
		message = sb.toString();

		ArrayList<User> users = new ArrayList<User>();
		users.add(toUser);
		users.add(otherUser);
		log.info("chatService.createChat: "+users+", "+message+", "+toUser.getCompany());
		Chat chat = chatService.createChat(users, message, toUser.getCompany());
		String newChatId = chat.getId().toString();

		// TODO - need to add one roolback system here, if the message couldn't
		// be delivered to the toUser.
		Iterator<IConnection> it = getConnections(conn.getScope().getConnections());
		while (it.hasNext()) {
			IConnection conn2 = it.next();
			if (conn2 instanceof IServiceCapableConnection) {
				IServiceCapableConnection service = (IServiceCapableConnection) conn2;
				IClient client = service.getClient();
				Object curUid = client.getAttribute("uid");

				if (toUID.equals(curUid)) {
					// link the client with the fromUID and the chatId. So it
					// uses the same chatId while the user is online.
					client.setAttribute(otherUID, oldChatId);
					log.info("invoke.messageReceived: "+otherUID+", "+otherUser.getName()+", "+message+", "+newChatId);
					service.invoke("messageReceived", new Object[] { otherUID, otherUser.getName(), message, newChatId });
				} else if (otherUID.equals(curUid)) {
					String otherMessage = "\nYou have been transfered to chat with " + toUser.getName();
					log.info("invoke.chatTransfered: "+oldChatId+", "+newChatId+", "+toUser.getId()+", "+toUser.getName()+", "+otherMessage);
					service.invoke("chatTransfered", new Object[] { oldChatId, newChatId, toUser.getId(), toUser.getName(), otherMessage });
				} else if (fromUID.equals(curUid)) {
					log.info("invoke.chatEnded: "+oldChatId);
					service.invoke("chatEnded", new Object[] { oldChatId });
				}
			}
		}

		closeDbContext(emf);
		return null;
	}

	protected Iterator<IConnection> getConnections(Collection<Set<IConnection>> connections) {
		Collection<IConnection> ret = new HashSet<IConnection>();
		for (Set<IConnection> set : connections) {
			ret.addAll(set);
		}
		return ret.iterator();
	}

	/* ----- Spring injected dependencies ----- */

	public void setApplication(Application app) {
		log.info("setApplication: "+app);
		this.app = app;
	}
}
