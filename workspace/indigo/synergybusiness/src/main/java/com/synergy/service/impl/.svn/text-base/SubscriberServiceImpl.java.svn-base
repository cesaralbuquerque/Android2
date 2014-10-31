package com.synergy.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.mail.SimpleEmail;

import com.synergy.dao.SubscriberDao;
import com.synergy.exception.SynergyException;
import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;
import com.synergy.service.CompanyService;
import com.synergy.service.GroupService;
import com.synergy.service.SubscriberService;
import com.synergy.util.CipherUtil;
import com.synergy.util.SendMail;
import com.synergy.util.SynergyConfig;
import com.synergy.util.SynergyMailUtil;
import com.synergy.util.Util;

public class SubscriberServiceImpl implements SubscriberService {

	private SubscriberDao subscriberDao;

	private CompanyService companyService;

	private GroupService groupService;

	public Subscriber authenticate(final String email, final String password) {
		return subscriberDao.authenticate(email, password);
	}

	public boolean isAdmin(Subscriber subscriber, Company company) {
		final List<Group> groups = groupService.groupsFromCompany(company);
		for (Group group : groups) {
			if (group.getPrivacy() == Group.ADMIN) {
				if (group.getUsers().contains(subscriber)) {
					return true;
				}
			}
		}
		return false;
	}

	public void addToCRM(Subscriber subscriber) {
		StringBuffer sb = new StringBuffer();
		sb.append("Name: ").append(subscriber.getName());
		sb.append("\nEmail: ").append(subscriber.getEmail());
		sb.append("\nCompany Name: ").append(subscriber.getCompany().getCompanyName());
		sb.append("\nPhone: ").append(subscriber.getCompany().getPhone());
		sb.append("\nWebsite: ").append(subscriber.getCompany().getWebsite());
		try {

			final String to = "patrickpiper@synergychat.com";
			final SimpleEmail email = SynergyMailUtil.createSupportSimpleMail("SynergyChat Support", "User Registered Notification", sb.toString(), to, "");
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public void emailJoinCompanyInvite(Subscriber subscriber, Company company, Collection<Group> groups, Collection<Product> products) {
		StringBuffer invParams = new StringBuffer();
		invParams.append(subscriber.getId()).append("|").append(company.getId()).append("|");
		for (Group group : groups) {
			invParams.append(group.getId()).append(";");
		}
		invParams.append("|");
		for (Product p : products) {
			invParams.append(p.getId()).append(";");
		}

		try {
			final String enc = CipherUtil.encrypt(invParams.toString());
			StringBuffer url = new StringBuffer(SynergyConfig.instance().getAdminSectionHost()).append("/join-company?invKey=").append(enc);
			System.out.println(url);
			StringBuilder sb = new StringBuilder();
			// sb.append("<html><body>");
			sb.append("Hi,");
			sb.append("\n\nYou have been invited to join the company ").append(company.getCompanyName()).append(" inside SynergyChat");
			if (subscriber.getCompany().equals(company)) {
				sb.append("\nClick the url below complete your registration process.");
			} else {
				sb.append("\nClick the url below to accept or deny this invitation.");
			}
			sb.append("\n").append(url);
			sb.append("\n\nThank You,");
			sb.append("\nSynergyChat Support Team");
			// sb.append("</body></html>");
			final SimpleEmail email = SynergyMailUtil.createSupportSimpleMail("SynergyChat Support", company.getCompanyName() + " added you on SynergyChat", sb.toString(), subscriber.getEmail(), subscriber.getName());
			email.send();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	 public void emailUserCreated(Subscriber sub, String pass) {
		StringBuilder sb = new StringBuilder();
		// sb.append("<html><body>");
		sb.append("Welcome to SynergyChat");
		Company client = sub.getCompany();
		sb.append("\n\nOne SynergyChat user has been created for you.");
		sb.append("\n\nTo login you can go to: ").append(
				SynergyConfig.instance().getAdminSectionHost());
		sb.append("\nThe user login information is: ");
		sb.append("\nEmail: ").append(sub.getEmail());
		sb.append("\nPassword: ").append(pass);
		sb.append("\n\nThank You,");
		sb.append("\nSynergyChat Support Team");
		// sb.append("</body></html>");
		try {
			final SimpleEmail email = SynergyMailUtil.createSupportSimpleMail(
					"SynergyChat Support", "SynergyChat User Created", sb
							.toString(), sub.getEmail(), sub.getName());
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public boolean emailInUse(String address) {
		final Subscriber sub = subscriberDao.findByEmail(address);
		if (sub != null) {
			// check if this subscriber is attached to a TEMP company
			return sub.getCompany() != null;

		}
		return false;
	}

	public Subscriber createIndependentSubscriber(String name, String email) {
		Subscriber sub = new Subscriber();
		sub.setEmail(email);
		sub.setName(name);
		sub.setDateCreated(new Date());
		subscriberDao.save(sub);
		return sub;
	}

	public Subscriber createCompanyIndependentSubscriber(Subscriber subscriber, String companyName, String website) {
		Company c = companyService.create(companyName, website, subscriber.getEmail());
		return addToCompany(c, groupService.groupsFromCompany(c), subscriber);
	}

	public Subscriber addToCompany(Company company, Collection<Group> groups, Subscriber sub) {
		sub = find(sub.getId());
		sub.getGroups().clear();
		sub.setCompany(company);
		for (Group g : groups) {
			g.getUsers().add(sub);
			sub.getGroups().add(g);
		}
		save(sub);
		return sub;
	}

	public Subscriber createSubscriberAndCompany(String name, String email, String password, String companyName, String website, String phone) {
		return createSubscriberAndCompany(name, email, password, companyName, website, phone, false);

	}

	public Subscriber createSubscriberAndCompany(String name, String email, String password, String companyName, String website, String phone, boolean confirmedStatus) {
		Company c = companyService.create(companyName, website, email, phone, confirmedStatus);
		Subscriber s = create(c, c.getGroups(), name, email, password);
		save(s);
		return s;
	}

	public Subscriber createSubscriberAndCompany(String name, String email, String password, String companyName, String website) {
		return createSubscriberAndCompany(name, email, password, companyName, website, null);

	}

	public Subscriber create(Company company, Collection<Group> groups, String name, String email, String password) {
		Subscriber sub = getByEmail(email);
		if (sub != null) {
			// if has company, it means that this user already is taken
			if (sub.getCompany() != null) {
				throw new SynergyException("EMAIL_IN_USE");
			}
		} else {
			// create new user for this email
			sub = new Subscriber();
			sub.setDateCreated(new Date());
			sub.setEmail(email);
		}
		sub.setCompany(company);
		sub.setName(name);
		sub.setPassword(Util.digest(password));
		subscriberDao.save(sub);
		return addToCompany(company, groups, sub);
	}

	public List<Subscriber> subscribersFromClient(Company client) {
		return subscriberDao.subscribersFromClient(client);
	}

	public void save(Subscriber user) {
		if (user.getId() == null) {
			subscriberDao.save(user);
		} else {
			subscriberDao.merge(user);
		}
	}

	public Subscriber find(Long id) {
		return subscriberDao.find(id);
	}

	public SubscriberDao getSubscriberDao() {
		return subscriberDao;
	}

	public void setSubscriberDao(final SubscriberDao dao) {
		this.subscriberDao = dao;
	}

	public void delete(Subscriber t) {
		// subscriberDao.delete(t);
		t = find(t.getId());
		t.setCompany(null);
		t.getGroups().clear();
		save(t);
	}

	public boolean emailPassword(String email) {
		final Subscriber user = subscriberDao.findByEmail(email);
		boolean found = false;
		if (user != null) {
			byte[] oldPass = user.getPassword();
			found = true;
			String newPass = randomPass();
			byte[] bpass = Util.digest(newPass);
			user.setPassword(bpass);
			try {
				SendMail sm = new SendMail();
				sm.postMail(new String[] { user.getEmail() }, "Reminder Email", "Your password is " + newPass, "support@synergychat.com");
				save(user);
			} catch (Exception e) {
				user.setPassword(oldPass);
				save(user);
				throw new RuntimeException(e.getMessage());
			}

		}
		return found;
	}

	private String randomPass() {
		final int rndNumber = (new Random().nextInt(10000) + 1) * 123456;
		String newPass = Long.toString(rndNumber, Character.MAX_RADIX);
		return newPass;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public Subscriber getByEmail(String email) {
		return subscriberDao.findByEmail(email);
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
}
