package com.synergy.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.synergy.model.Company;
import com.synergy.model.Group;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;
import com.synergy.service.AuthenticateService;
import com.synergy.service.BrainTreeService;
import com.synergy.service.CompanyService;
import com.synergy.service.GroupService;
import com.synergy.service.ProductService;
import com.synergy.service.SubscriberService;
import com.synergy.util.SynergyConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestSubscriber {

	private SubscriberService subscriberService;

	private CompanyService companyService;

	private GroupService groupService;

	private ProductService productService;

	private AuthenticateService authenticateService;

	private BrainTreeService braintreeService;

	private EntityManagerFactory entityManagerFactory;

	@Test
	public void setUp() throws IOException {
		try {
			 
			Class.forName("com.mysql.jdbc.Driver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
 
		}
		final InputStream in = this.getClass().getClassLoader().getResourceAsStream("synergyengine.properties");
		if (in == null) {
			Assert.fail("synergyengine.properties not found");
		}
		SynergyConfig.instance().load(in);

		Assert.assertNotNull(braintreeService);

		// create the Products
		Product product = new Product();
		product.setName(Product.VMAIL);
		productService.save(product);
		Assert.assertEquals(product.getId(), Long.valueOf(1));

		product = new Product();
		product.setName(Product.VCHAT);
		productService.save(product);
		Assert.assertEquals(product.getId(), Long.valueOf(2));
	}

	@Test
	public void testCreateSubscriberAndCompany() throws Exception {
		Subscriber s = subscriberService.createSubscriberAndCompany("Fabiano", "fabiano@test.com", "f", "Fabiano Company", "website", "99782607");
		Assert.assertNotNull(s);

		Assert.assertNotNull(s.getCompany());

		Assert.assertEquals("99782607", s.getCompany().getPhone());

		final boolean emailInUse = subscriberService.emailInUse("fabiano@test.com");
		Assert.assertTrue(emailInUse);
	}

	@Test
	public void testAuthenticateInvalidSubscriber() throws Exception {
		String sessionId = authenticateService.authenticate("wrong@test.com", "f");
		Assert.assertNull(sessionId);
	}

	@Test
	public void testAuthenticateSubscriber() throws Exception {
		String sessionId = authenticateService.authenticate("fabiano@test.com", "f");
		Assert.assertNotNull(sessionId);
		Subscriber sub = authenticateService.validateSessionId(sessionId);
		Assert.assertNotNull(sub);
		Assert.assertNotNull(sub.getCompany());
		Assert.assertEquals(sub.getEmail(), "fabiano@test.com");
	}

	@Test
	public void testAuthenticateSubscriberExpiredSession() throws Exception {
		String sessionId = authenticateService.authenticateDate("fabiano@test.com", "f", new Date((2007 - 1900), 10, 10));
		Assert.assertNotNull(sessionId);

		Subscriber sub = authenticateService.validateSessionId(sessionId);
		Assert.assertNull(sub);
	}

	@Test
	public void testDefaultGroupFromCompany() throws Exception {
		Subscriber s = subscriberService.getByEmail("fabiano@test.com");
		Assert.assertNotNull(s.getCompany());
		final Company company = companyService.find((long) 1);
		Assert.assertNotNull(company);
		final List<Group> groups = groupService.groupsFromCompany(company);
		Assert.assertEquals(groups.size(), 2);

		s = subscriberService.find(s.getId());
		Assert.assertNotNull(s);
		boolean groupDefaultOk = false;
		boolean groupAdminOk = false;
		for (Group group : groups) {
			if (group.getPrivacy() == Group.ADMIN) {
				groupAdminOk = true;
			} else if (group.getPrivacy() == Group.PRIVATE && "Default".equals(group.getName())) {
				groupDefaultOk = true;
			}
			Assert.assertTrue(group.getUsers().contains(s));
		}
		Assert.assertTrue(groupDefaultOk);
		Assert.assertTrue(groupAdminOk);

		Assert.assertEquals(s.getGroups().size(), 2);
	}

	@Test
	public void testGetSubscriberFromCompany() throws Exception {
		final Company company = companyService.find((long) 1);
		Subscriber s = subscriberService.getByEmail("fabiano@test.com");
		List<Subscriber> c = subscriberService.subscribersFromClient(company);
		Assert.assertEquals(c.size(), 1);

		Assert.assertEquals(c.get(0), s);
	}

	@Test
	public void testAddSubscriberToCompany() throws Exception {
		final Company company = companyService.find((long) 1);
		Assert.assertNotNull(company);
		final List<Group> groups = groupService.groupsFromCompany(company);
		Group g = null;
		for (Group group : groups) {
			if (group.getPrivacy() == Group.PRIVATE) {
				g = group;
				break;
			}
		}
		ArrayList<Group> sg = new ArrayList<Group>();
		sg.add(g);
		Subscriber sub = subscriberService.create(company, sg, "Brian", "brian@test.com", "b");
		sub = subscriberService.getByEmail(sub.getEmail());
		Assert.assertEquals(company, sub.getCompany());

		sub = subscriberService.getByEmail(sub.getEmail());
		Assert.assertTrue(sub.getGroups().contains(g));

		Assert.assertEquals(company, g.getCompany());
	}

	@Test
	public void testAssignProductToSubscriber() {
		final List<Product> products = new ArrayList<Product>(productService.getAll());
		Assert.assertEquals(products.size(), 2);
		final Company company = companyService.find((long) 1);
		final Subscriber brian = subscriberService.getByEmail("brian@test.com");

		productService.updateProducts(brian, products);

		List<Product> brianProducts = productService.getProducts(brian);
		Assert.assertEquals(brianProducts.size(), 2);

		Product p = brianProducts.get(0);
		ArrayList<Product> newBrianP = new ArrayList<Product>();
		newBrianP.add(p);
		productService.updateProducts(brian, newBrianP);
		final List<Product> brianProducts2 = productService.getProducts(brian);
		Assert.assertEquals(brianProducts2.size(), 1);
		Assert.assertEquals(brianProducts2.get(0), p);
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
	public void setBraintreeService(BrainTreeService braintreeService) {
		this.braintreeService = braintreeService;
	}

	@Resource
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Resource
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Resource
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	@Resource
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Resource
	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}
}
