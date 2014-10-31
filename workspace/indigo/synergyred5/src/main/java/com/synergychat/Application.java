package com.synergychat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
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

import com.synergy.model.Company;
import com.synergy.model.GuestChat;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;
import com.synergy.model.User;
import com.synergy.service.AuthenticateService;
import com.synergy.service.CompanyService;
import com.synergy.service.GroupService;
import com.synergy.service.GuestChatService;
import com.synergy.service.MailContactService;
import com.synergy.service.ProductService;
import com.synergy.service.PurchaseService;
import com.synergy.service.SubscriberService;
import com.synergy.service.UserService;
import com.synergy.util.SynergyConfig;
import com.synergy.util.Util;
import com.synergychat.dto.DtoUtil;
import com.synergychat.dto.GroupDTO;
import com.synergychat.dto.SubscriberDTO;

public class Application extends ApplicationAdapter {

	public static final Byte ONLINE = 1;

	public static final Byte OFFLINE = 2;

	public static final Byte BUSY = 3;

	public static final Byte AWAY = 4;

	public static final String VISITOR_UID = "visitor_uid";

	public static final String CLIENT_ID = "clientId";

	public static final String USER_ID = "uid";

	public static final String SYSTEM_IN = "SYSTEM_IN";

	boolean participate;

	private ClassLoader refClassLoader = Thread.currentThread()
			.getContextClassLoader();

	private ClassLoader origLoader = Thread.currentThread()
			.getContextClassLoader();

	// private static final Log log = LogFactory.getLog(Application.class);

	private static Logger log = Red5LoggerFactory.getLogger(Application.class);

	private static final String MAIN_UI = "MAIN_UI";

	private static final HashMap<String, Byte> userPresenceState = new HashMap<String, Byte>();

	public Application() {
		// clientMgr = new ClientManager("users_so", true);
	}

	static HashSet<String> users = new HashSet<String>();

	private void toggleClassLoader() {
		if (refClassLoader == origLoader) {
			Thread.currentThread().setContextClassLoader(
					getClass().getClassLoader());
		} else {
			Thread.currentThread().setContextClassLoader(origLoader);
		}
	}

	public void setPresenceState(String state) {
		log.info("setPresenceState: " + state);
		IConnection conn = Red5.getConnectionLocal();
		setPresenceState(conn.getScope(), conn.getClient(), Byte
				.parseByte(state));
	}

	public void setPresenceState(IScope scope, IClient client, Byte state) {
		log.info("setPresenceState: " + scope + ", " + client + ", " + state);

		String uid = (String) client.getAttribute(USER_ID);
		/*
		 * WebApplicationContext springContext = getSpringContext();
		 * UserPresenceService presenceService = (UserPresenceService)
		 * springContext.getBean("presenceService");
		 * presenceService.setPresenceState(uid, state);
		 */
		// client.setAttribute("presenceState", Byte.valueOf(state));
		userPresenceState.put(uid, Byte.valueOf(state));
		Iterator<IConnection> it = getConnections(scope.getConnections());
		while (it.hasNext()) {
			IConnection conn2 = it.next();
			if (conn2 instanceof IServiceCapableConnection) {
				IServiceCapableConnection service = (IServiceCapableConnection) conn2;
				if (service.getClient() != null) {
					Object toUID = service.getClient().getAttribute(USER_ID);
					if (toUID != null && !uid.equals(toUID)) {
						service.invoke("presenceStateChanged", new Object[] {
								uid, state });
					}
				}
			}
		}
	}

	/*
	 * Return clientId;-;username;-;online
	 */
	public String getUserInfo(String userId) {
		log.info("getUserInfo: " + userId);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			UserService userService = (UserService) springContext
					.getBean("userService");
			log.info("userService.find: " + userId);
			User user = userService.find(Long.parseLong(userId));
			if (user == null) {
				return null;
			}
			// 0=offline; 1=online
			String state = "0";
			/*
			 * for (IClient redClient : conn.getScope().getClients()) { if
			 * (user.getId().toString().equals(redClient.getAttribute(USER_ID)))
			 * { Byte st = (Byte) redClient.getAttribute("presenceState"); if
			 * (st == ONLINE) { state = "1"; } break; } }
			 */

			Byte st = (Byte) userPresenceState.get(userId);
			if (st == ONLINE) {
				state = "1";
			}
			return user.getCompany().getId() + ";-;" + user.getName() + ";-;"
					+ state;
		} finally {
			closeDbContext(emf);
		}
	}

	public Object getGroups() {
		log.info("getGroups");
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();
		String cid = (String) conn.getClient().getAttribute(CLIENT_ID);
		CompanyService clientService = (CompanyService) springContext
				.getBean("companyService");
		GroupService groupService = (GroupService) springContext
				.getBean("groupService");

		EntityManagerFactory emf = startDbContext();
		try {
			log.info("clientService.find: " + cid);
			Company client = clientService.find(Long.parseLong(cid));
			log.info("DtoUtil.convertGroup: " + client);
			final List<GroupDTO> groupsDTO = DtoUtil.convertGroup(groupService
					.groupsFromCompany(client));
			for (GroupDTO groupDTO : groupsDTO) {
				for (SubscriberDTO subDTO : groupDTO.getUsers()) {
					for (IClient redClient : conn.getScope().getClients()) {
						if (subDTO.getId().toString().equals(
								redClient.getAttribute(USER_ID))) {
							// Byte state = (Byte)
							// redClient.getAttribute("presenceState");
							Byte state = (Byte) userPresenceState.get(subDTO
									.getId().toString());
							subDTO.setPresenceState(state == null ? OFFLINE
									: state);
							break;
						}
					}
				}
			}
			return groupsDTO;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public Object getMailContacts() {
		log.info("getMailContacts");
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			MailContactService mailContactService = (MailContactService) springContext
					.getBean("mailContactService");
			SubscriberService subscriberService = (SubscriberService) springContext
					.getBean("subscriberService");
			String UID = (String) conn.getClient().getAttribute(USER_ID);
			log.info("subscriberService.find: " + UID);
			Subscriber subscriber = subscriberService.find(Long.parseLong(UID));
			log.info("convertMailContact: " + subscriber);
			return DtoUtil.convertMailContact(mailContactService
					.getAll(subscriber));
		} finally {
			closeDbContext(emf);
		}
	}

	public SubscriberDTO getSubscriberLogged() {
		log.info("getSubscriberLogged");
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			SubscriberService subscriberService = (SubscriberService) springContext
					.getBean("subscriberService");
			String UID = (String) conn.getClient().getAttribute(USER_ID);
			log.info("subscriberService.find: " + UID);
			Subscriber subscriber = subscriberService.find(Long.parseLong(UID));
			return DtoUtil.convertSubscriber(subscriber);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public void editProfile(String email, String name) {
		log.info("editProfile: " + email + ", " + name);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			SubscriberService subscriberService = (SubscriberService) springContext
					.getBean("subscriberService");
			String UID = (String) conn.getClient().getAttribute(USER_ID);
			log.info("subscriberService.find: " + UID);
			Subscriber subscriber = subscriberService.find(Long.parseLong(UID));
			if (!subscriber.getEmail().equalsIgnoreCase(email)) {
				log.info("subscriberService.emailInUse: " + email);
				if (subscriberService.emailInUse(email)) {
					throw new Exception("Email already in use.");
				}
			}
			subscriber.setEmail(email);
			subscriber.setName(name);
			log.info("subscriberService.save: " + subscriber);
			subscriberService.save(subscriber);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public Boolean forgotPassword(String email) {
		log.info("forgotPassword: " + email);
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			SubscriberService subscriberService = (SubscriberService) springContext
					.getBean("subscriberService");
			log.info("subscriberService.emailPassword: " + email);
			return subscriberService.emailPassword(email);
		} finally {
			closeDbContext(emf);
		}
	}

	public String changePassword(String oldpass, String pass, String retPass) {
		log.info("changePassword: " + oldpass + ", " + pass + ", " + retPass);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			SubscriberService subscriberService = (SubscriberService) springContext
					.getBean("subscriberService");
			String UID = (String) conn.getClient().getAttribute(USER_ID);
			log.info("subscriberService.find: " + UID);
			Subscriber subscriber = subscriberService.find(Long.parseLong(UID));
			if (oldpass == null || oldpass.trim().length() == 0) {
				return "The Current Password can't be empty.";
			} else {
				if (pass != null && pass.trim().length() == 0) {
					return "The New Password can't be empty.";
				}

				byte[] curpass = subscriber.getPassword();
				byte[] boldpass = Util.digest(oldpass);
				if (!Arrays.equals(curpass, boldpass)) {
					return "The Current Password doesn't match.";
				}
				if (!pass.equals(Util.toString(retPass))) {
					return "The New Password doesn't match the Retyped Password.";
				}
			}
			subscriber.setPassword(Util.digest(pass));
			subscriberService.save(subscriber);
		} finally {
			closeDbContext(emf);
		}
		return null;
	}

	public synchronized boolean start(IScope scope) {
		log.info("start: " + scope);
		log.debug("Debug: SynergyChat Started");
		System.out.println("Synergy Started");
		try {
			final InputStream in = scope.getContext().getResource(
					"WEB-INF\\synergyengine.properties").getInputStream();
			SynergyConfig.instance().load(in);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			log.error("SynergyEngine properties not loaded", e);
		}
		return super.start(scope);
	}

	protected EntityManagerFactory lookupEntityManagerFactory() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		return (EntityManagerFactory) wac.getBean("entityManagerFactory",
				EntityManagerFactory.class);
	}

	protected EntityManager createEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

	public void closeDbContext(EntityManagerFactory emf) {
		log.info("closeDbContext: " + emf);
		toggleClassLoader();
		if (!participate) {
			EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager
					.unbindResource(emf);
			emHolder.getEntityManager().clear();
			emHolder.getEntityManager().close();
		}
	}

	public EntityManagerFactory startDbContext() {
		log.info("startDbContext");
		toggleClassLoader();
		EntityManagerFactory emf;
		emf = lookupEntityManagerFactory();
		participate = false;
		if (TransactionSynchronizationManager.hasResource(emf)) {
			participate = true;
		} else {
			try {
				EntityManager em = createEntityManager(emf);
				em.clear();
				TransactionSynchronizationManager.bindResource(emf,
						new EntityManagerHolder(em));
			} catch (PersistenceException ex) {
				log.error(ex.getMessage(), ex);
				throw new DataAccessResourceFailureException(
						"Could not create JPA EntityManager", ex);
			}
		}
		return emf;
	}

	public WebApplicationContext getSpringContext() {
		log.info("getSpringContext");
		ServletContext ctx = getServletContext();
		WebApplicationContext springContext = (WebApplicationContext) ctx
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return springContext;
	}

	public ServletContext getServletContext() {
		log.info("getServletContext");
		ApplicationContext appCtx = getContext().getApplicationContext();
		ServletContext ctx = ((XmlWebApplicationContext) appCtx)
				.getServletContext();
		return ctx;
	}

	public String createSessionId() {
		IConnection conn = Red5.getConnectionLocal();
		EntityManagerFactory emf = startDbContext();
		try {
			AuthenticateService authService = (AuthenticateService) getSpringContext()
					.getBean("authenticateService");
			SubscriberService subService = (SubscriberService) getSpringContext()
					.getBean("subscriberService");
			String uid = (String) conn.getClient().getAttribute(USER_ID);
			String sessionId = authService.generateSessionId(subService
					.find(Long.parseLong(uid)));

			return sessionId;
		} finally {
			emf.close();
		}
	}

	public SubscriberDTO validateSession(String sessionId) {
		return validateSession(sessionId, false);
	}

	public SubscriberDTO validateSession(String sessionId, boolean mainUI) {
		log.info("validateSession: " + sessionId);
		EntityManagerFactory emf = startDbContext();
		try {
			AuthenticateService authService = (AuthenticateService) getSpringContext()
					.getBean("authenticateService");
			SubscriberService subService = (SubscriberService) getSpringContext()
					.getBean("subscriberService");
			log.info("authService.validateSessionId: " + sessionId);
			Subscriber subscriber = authService.validateSessionId(sessionId);
			if (subscriber == null) {
				return null;
			}
			setClientAttributes(String.valueOf(subscriber.getId()), subscriber
					.getName(),
					String.valueOf(subscriber.getCompany().getId()), mainUI);
			disconnectOtherUsers(subscriber.getId().toString());

			boolean vmail = false;
			boolean vchat = false;
			ProductService productService = (ProductService) getSpringContext()
					.getBean("productService");
			log.info("productService.getProducts: " + subscriber);
			Collection<Product> products = productService
					.getProducts(subscriber);
			for (Product p : products) {
				if (p.getName().equals(Product.VCHAT)) {
					vchat = true;
				} else if (p.getName().equals(Product.VMAIL)) {
					vmail = true;
				}
			}
			final boolean admin = subService.isAdmin(subscriber, subscriber
					.getCompany());
			closeDbContext(emf);
			return DtoUtil.convertSubscriber(subscriber, vmail, vchat, admin);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			emf.close();
		}
	}

	private void setClientAttributes(String userId, String nickname,
			String companyId, boolean mainUI) {
		log.info("setClienteAttributes: " + userId + ", " + nickname + ", "
				+ companyId);
		IConnection conn = Red5.getConnectionLocal();
		final IScope curScope = conn.getScope();
		final String scopeName = "" + companyId;
		IScope newScope = curScope.getScope(scopeName);
		if (newScope == null) {
			curScope.createChildScope(scopeName);
			newScope = curScope.getScope(scopeName);
		}
		conn.connect(newScope);
		Red5.setConnectionLocal(conn);
		IClient client = conn.getClient();
		client.setAttribute(USER_ID, userId);
		client.setAttribute("nickname", nickname);
		if (mainUI) {
			client.setAttribute(SYSTEM_IN, MAIN_UI);
		}
		conn.getClient().setAttribute(Application.CLIENT_ID, companyId);
	}

	private Subscriber authenticate(String email, String pass) {
		SubscriberService serv = (SubscriberService) getSpringContext()
				.getBean("subscriberService");
		log.info("serv.authenticate: " + email + ", " + pass);
		return serv.authenticate(email, pass);
	}

	public SubscriberDTO loginGuest(String name, String email, String toUID) {
		log.info("loginGuest: " + name + ", " + email + ", " + toUID);
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			SubscriberService subscriberService = (SubscriberService) springContext
					.getBean("subscriberService");
			GuestChatService guestChatService = (GuestChatService) springContext
					.getBean("guestChatService");
			log.info("subscriberService.find: " + toUID);
			Subscriber s = subscriberService.find(Long.parseLong(toUID));
			log.info("guestChatService.createGuestChat: " + name + ", " + email
					+ ", " + s.getCompany());
			GuestChat gc = guestChatService.createGuestChat(name, email, s
					.getCompany());
			setClientAttributes(String.valueOf(gc.getId()), gc.getName(),
					String.valueOf(gc.getCompany().getId()), false);
			return new SubscriberDTO(gc.getId(), gc.getName(), gc.getEmail());
		} finally {
			emf.close();
		}
	}

	public SubscriberDTO registerSubscriber(String name, String email,
			String password) {
		log
				.info("registerSubscriber: " + name + ", " + email + ", "
						+ password);
		SubscriberService serv = (SubscriberService) getSpringContext()
				.getBean("subscriberService");
		CompanyService cserv = (CompanyService) getSpringContext().getBean(
				"companyService");
		ProductService productService = (ProductService) getSpringContext()
				.getBean("productService");
		PurchaseService purchaseService = (PurchaseService) getSpringContext()
				.getBean("purchaseService");
		EntityManagerFactory emf = startDbContext();
		try {
			Subscriber subscriber = serv.createSubscriberAndCompany(name,
					email, password, name, null, null, true);
			Product vmail = productService.getProductByName(Product.VMAIL);
			purchaseService.purchaseProduct(subscriber.getCompany(), vmail, 1,
					0, "1 Month Free", null);
			Product vchat = productService.getProductByName(Product.VCHAT);
			purchaseService.purchaseProduct(subscriber.getCompany(), vchat, 1,
					0, "1 Month Free", null);
			ArrayList<Product> products = new ArrayList<Product>();
			products.add(vmail);
			products.add(vchat);
			productService.updateProducts(subscriber, products);

			serv.emailUserCreated(subscriber, password);
			serv.addToCRM(subscriber);

			setClientAttributes(String.valueOf(subscriber.getId()), subscriber
					.getName(),
					String.valueOf(subscriber.getCompany().getId()), false);
			closeDbContext(emf);
			return DtoUtil.convertSubscriber(subscriber, true, true, true);
		} finally {
			emf.close();
		}
	}

	public SubscriberDTO loginByEmail(String email) {
		log.info("loginByEmail: " + email);
		IConnection conn = Red5.getConnectionLocal();
		EntityManagerFactory emf = startDbContext();
		try {
			SubscriberService serv = (SubscriberService) getSpringContext()
					.getBean("subscriberService");
			Subscriber subscriber = serv.getByEmail(email);
			if (subscriber == null) {
				return null;
			}

			setClientAttributes(String.valueOf(subscriber.getId()), subscriber
					.getName(),
					String.valueOf(subscriber.getCompany().getId()), false);

			boolean vmail = false;
			boolean vchat = false;
			ProductService productService = (ProductService) getSpringContext()
					.getBean("productService");
			log.info("productService.getProducts: " + subscriber);
			Collection<Product> products = productService
					.getProducts(subscriber);
			for (Product p : products) {
				if (p.getName().equals(Product.VCHAT)) {
					vchat = true;
				} else if (p.getName().equals(Product.VMAIL)) {
					vmail = true;
				}
			}
			final boolean admin = serv.isAdmin(subscriber, subscriber
					.getCompany());
			closeDbContext(emf);

			return DtoUtil.convertSubscriber(subscriber, vmail, vchat, admin);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			emf.close();
		}
	}

	public SubscriberDTO loginSubscriber(String email, String pass) {
		return loginSubscriber(email, pass, false);
	}

	public SubscriberDTO loginSubscriber(String email, String pass,
			boolean mainUI) {
		log.info("loginSubscriber: " + email + ", " + pass);
		EntityManagerFactory emf = startDbContext();
		try {
			Subscriber subscriber = null;
			subscriber = authenticate(email, pass);
			if (subscriber == null) {
				return null;
				// throw new
				// IllegalArgumentException("The email and/or password is invalid.");
			}
			// TODO - check if user is active
			// if (!subscriber.getClient().getAccountActive()) {
			// throw new
			// IllegalStateException("The account for this client is not active.");
			// }

			String subId = subscriber.getId().toString();

			setClientAttributes(subId, subscriber.getName(), String
					.valueOf(subscriber.getCompany().getId()), mainUI);
			disconnectOtherUsers(subId);

			// TODO - review when add other modules.
			// client.setAttribute(CLIENT_ID,
			// subscriber.getClient().getId().toString());
			// Collection<Group> groups = subscriber.getGroups();
			// ArrayList<Long> gids = new ArrayList<Long>();
			// for (Group group : groups) {
			// gids.add(group.getId());
			// }
			// client.setAttribute("gIds", gids);
			boolean vmail = false;
			boolean vchat = false;
			ProductService productService = (ProductService) getSpringContext()
					.getBean("productService");
			SubscriberService subscriberService = (SubscriberService) getSpringContext()
					.getBean("subscriberService");
			log.info("productService.getProducts: " + subscriber);
			Collection<Product> products = productService
					.getProducts(subscriber);
			for (Product p : products) {
				if (p.getName().equals(Product.VCHAT)) {
					vchat = true;
				} else if (p.getName().equals(Product.VMAIL)) {
					vmail = true;
				}
			}
			final boolean admin = subscriberService.isAdmin(subscriber,
					subscriber.getCompany());
			closeDbContext(emf);

			return DtoUtil.convertSubscriber(subscriber, vmail, vchat, admin);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			emf.close();
		}
	}

	private void disconnectOtherUsers(String subId) {
		IConnection conn = Red5.getConnectionLocal();
		// if this user already is logged in another session, then he needs
		// to
		// be disconnected.
		final IClient thisClient = conn.getClient();
		if (!MAIN_UI.equals(thisClient.getAttribute(SYSTEM_IN))) {
			// only disconnect other session, if user is connected to the main
			// ui.
			return;
		}
		final IScope curScope = conn.getScope();
		Iterator<IConnection> it = getConnections(curScope.getConnections());
		while (it.hasNext()) {
			IConnection conn2 = it.next();
			if (conn2 instanceof IServiceCapableConnection) {
				IServiceCapableConnection service = (IServiceCapableConnection) conn2;
				IClient curClient = service.getClient();
				if (curClient != null && curClient != thisClient) {
					Object curUid = curClient.getAttribute(USER_ID);

					// only disconnect the user if he is in the MainUI system.
					if (subId.equals(curUid)
							&& MAIN_UI
									.equals(curClient.getAttribute(SYSTEM_IN))) {
						// disconnect the user
						curClient.disconnect();
						break;
					}
				}
			}
		}
	}

	public synchronized boolean connect(IConnection conn, IScope scope,
			Object params[]) {
		log.info("connect: " + conn + ", " + scope + ", " + params);
		System.out.println((new StringBuilder("Connected: ")).append(
				conn.toString()).toString());
		System.out.println(scope.toString());
		if (!super.connect(conn, scope, params)) {
			return false;
		}

		if (params == null) {
			return true;
		}
		if (params.length == 0 || params.length == 1) {
			return true;
		}

		if (params.length >= 2) {
			if ("synuser".equals(params[0]) && "synpass".equals(params[1])) {
				return true;
			}
		}
		return false;
	}

	public synchronized void disconnect(IConnection conn, IScope scope) {
		log.info("disconnect: " + conn + ", " + scope);
		IClient client = conn.getClient();

		// TODO - review when add other modules.
		// if visitor, then only remove from visitor lists
		// Object visitorId = client.getAttribute(VISITOR_UID);
		// if (visitorId != null) {
		// onVisitorExitPage(visitorId.toString());
		// super.disconnect(conn, scope);
		// return;
		// }

		users.remove(client.getAttribute("uname"));

		String name = (String) client.getAttribute(USER_ID);
		if (name != null) {
			if (Boolean.TRUE.equals(client.getAttribute("disconnected"))) {
				return;
			}
			client.setAttribute("disconnected", Boolean.TRUE);
			// TODO - review when add other modules.
			// if (Boolean.TRUE.equals(client.getAttribute("guest"))) {
			// removeFromWaitList(scope, client);
			// } else {
			setPresenceState(scope, client, OFFLINE);
			// }

			Iterator<IConnection> it = getConnections(conn.getScope()
					.getConnections());
			while (it.hasNext()) {
				IConnection conn2 = it.next();
				if (conn2 instanceof IServiceCapableConnection) {
					IServiceCapableConnection service = (IServiceCapableConnection) conn2;
					IClient cur = service.getClient();
					Object curUid = cur.getAttribute(USER_ID);

					if (!name.equals(curUid)) {
						service.invoke("userDisconnected",
								new Object[] { name });
					}
				}
			}

		}
		// clientMgr.removeClient(scope, name);
		super.disconnect(conn, scope);
	}

	protected Iterator<IConnection> getConnections(
			Collection<Set<IConnection>> connections) {
		Collection<IConnection> ret = new HashSet<IConnection>();
		for (Set<IConnection> set : connections) {
			ret.addAll(set);
		}
		return ret.iterator();
	}

}